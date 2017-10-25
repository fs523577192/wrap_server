package org.firas.wrap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.firas.jiadian.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    Activity findFirstByNameAndStatusNot(String name, byte status);

    Page<Activity> findByNameContainingAndStatusNot(
            String name, byte status, Pageable pageable);

    Page<Activity> findByStatusNot(byte status, Pageable pageable);
}
