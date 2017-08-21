package org.firas.wrap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.firas.wrap.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Integer> {

    List<Box> findByNameContainingAndStatus(String name, Byte status);
}
