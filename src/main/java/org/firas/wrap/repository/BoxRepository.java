package org.firas.wrap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.firas.wrap.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Integer> {

    Box findFirstByNameAndStatusNot(String name, byte status);

    Page<Box> findByStatus(byte status, PageRequest pageRequest);

    Page<Box> findByNameContainingAndStatus(
            String name, byte status, PageRequest pageRequest);
}
