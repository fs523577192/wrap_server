package org.firas.wrap.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.firas.wrap.entity.Box;
import org.firas.wrap.entity.BoxComponent;
import org.firas.wrap.entity.embeddable.BoxComponentId;

public interface BoxComponentRepository
        extends JpaRepository<BoxComponent, BoxComponentId> {

    @Modifying
    @Query("UPDATE BoxComponent SET updateTime = ?1, status = ?2 " +
            "WHERE boxComponentId.boxId = ?3 AND " +
            "boxComponentId.componentId IN ?4")
    int setStatusByIds(Date now, Byte status, Integer boxId, List<Integer> ids);

    List<BoxComponent> findByBox(Box box);
    List<BoxComponent> findByBoxAndStatus(Box box, Byte status);
}
