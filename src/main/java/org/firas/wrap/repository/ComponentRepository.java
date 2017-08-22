package org.firas.wrap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.firas.wrap.entity.Component;

public interface ComponentRepository extends JpaRepository<Component, Integer> {

    Component findFirstByNameAndStatusNot(String name, byte status);

    List<Component> findByNameContainingAndStatus(String name, Byte status);
}
