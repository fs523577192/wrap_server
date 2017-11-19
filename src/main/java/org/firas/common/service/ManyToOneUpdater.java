package org.firas.common.service;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.firas.common.model.StatusModel;

public abstract class ManyToOneUpdater<ID, M extends StatusModel> {

    private Byte oldStatus, newStatus;

    protected ManyToOneUpdater(Byte oldStatus, Byte newStatus) {
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    protected ManyToOneUpdater() {
        this(StatusModel.STATUS_DELETED, StatusModel.STATUS_NORMAL);
    }

    protected abstract ID getId(M model);

    /**
     * 
     * @return int  the number of rows changed
     */
    protected abstract int setStatusByIds(List<ID> ids, Byte status);

    protected abstract void batchCreate(Collection<M> models);
    protected void batchUpdate(Collection<M> models) {}
    protected boolean batchSave(Collection<M> models) {
        return false;
    }

    protected boolean modelEquals(M a, M b) {
        return true;
    }

    public void update(Collection<M> originalOnes, Collection<M> newOnes) {
        Map<ID, M> map = new HashMap<ID, M>(newOnes.size(), 1f);
        for (M item : newOnes) {
            map.put(getId(item), item);
        }

        List<M> toSave = new ArrayList<M>();
        List<M> toUpdate = new ArrayList<M>();
        List<ID> activateIds = new ArrayList<ID>();
        List<ID> deleteIds = new ArrayList<ID>();
        for (M item : originalOnes) {
            ID id = getId(item);
            M newOne = map.get(id);
            if (null != newOne) {
                if (!modelEquals(newOne, item)) {
                    toSave.add(newOne);
                    toUpdate.add(newOne);
                } else if (!newStatus.equals(item.getStatus())) {
                    newOne.setStatus(newStatus);
                    toSave.add(newOne);
                    activateIds.add(id);
                }
                map.remove(id);
            } else {
                if (!oldStatus.equals(item.getStatus())) {
                    item.setStatus(oldStatus);
                    toSave.add(item);
                    deleteIds.add(id);
                }
            }
        }

        if (!batchSave(toSave)) {
            multiSave(activateIds, deleteIds, toUpdate, map);
        }
    }

    private void multiSave(
                List<ID> activateIds, List<ID> deleteIds,
                List<M> toUpdate, Map<ID, M> map) {
        if (!activateIds.isEmpty()) {
            setStatusByIds(activateIds, newStatus);
        }
        if (!deleteIds.isEmpty()) {
            setStatusByIds(deleteIds, oldStatus);
        }

        if (!toUpdate.isEmpty()) {
            batchUpdate(toUpdate);
        }

        Collection<M> toCreate = map.values();
        for (M item : toCreate) {
            item.setStatus(newStatus);
        }
        if (!toCreate.isEmpty()) {
            batchCreate(toCreate);
        }
    }
}
