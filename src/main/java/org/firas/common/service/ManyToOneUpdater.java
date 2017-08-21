package org.firas.common.service;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.firas.common.model.StatusModel;

public abstract class ManyToOneUpdater<ID, M extends StatusModel> {

    protected abstract ID getId(M model);

    /**
     * 
     * @return int  the number of rows changed
     */
    protected abstract int activateByIds(List<ID> ids);

    /**
     * 
     * @return int  the number of rows changed
     */
    protected abstract int deleteByIds(List<ID> ids);

    protected abstract void batchCreate(Collection<M> models);

    public void update(List<M> originalOnes, List<M> newOnes) {
        Map<ID, M> map = new HashMap<ID, M>();
        for (M item : newOnes) {
            map.put(getId(item), item);
        }

        List<ID> activateIds = new ArrayList<ID>();
        List<ID> deleteIds = new ArrayList<ID>();
        for (M item : originalOnes) {
            ID id = getId(item);
            if (map.containsKey(id)) {
                if (!item.isStatusNormal()) {
                    activateIds.add(id);
                }
                map.remove(id);
            } else {
                if (!item.isStatusDeleted()) {
                    deleteIds.add(id);
                }
            }
        }

        if (!activateIds.isEmpty()) activateByIds(activateIds);
        if (!deleteIds.isEmpty()) deleteByIds(deleteIds);

        Collection<M> toCreate = map.values();
        if (!toCreate.isEmpty()) batchCreate(toCreate);
    }
}
