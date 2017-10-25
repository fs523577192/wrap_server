package org.firas.wrap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.firas.jiadian.entity.Activity;
import org.firas.jiadian.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByNameAndStatusNot(String name, byte status);

    Page<Order> findByNameContainingAndStatusNot(
            String name, byte status, Pageable pageable);

    Page<Order> findByActivityAndStatusNot(
            Activity activity, byte status, Pageable pageable);
}
