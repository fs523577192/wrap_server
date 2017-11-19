package org.firas.wrap.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.firas.wrap.entity.Component;

public interface ComponentRepository extends JpaRepository<Component, Integer> {

    Component findFirstByNameAndStatusNot(String name, byte status);

    List<Component> findByIdInAndStatusNot(Collection<Integer> ids, byte status);

    Page<Component> findByNameContainingAndStatusNot(
            String name, byte status, Pageable pageable);

    Page<Component> findByStatusNot(byte status, Pageable pageable);
}
