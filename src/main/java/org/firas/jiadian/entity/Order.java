package org.firas.jiadian.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

/**
 * 维修单
 */
@Entity
@Table(name = "t_order")
public class Order extends org.firas.common.model.StatusModel {

    @Column
    @Getter @Setter private String mobile; // 物主手机

    @Column
    @Getter @Setter private String phone; // 物主固话

    @Column
    @Getter @Setter private String email; // 物主email

    @Column(name = "school_idcode")
    @Getter @Setter private String schoolIdCode; // 物主学号

    @Column
    @Getter @Setter private String owner; // 物主

    @Column
    @Getter @Setter private String name; // 物品名称

    @Column
    @Getter @Setter private String worker; // 接修人

    @Column
    @Getter @Setter private String reason; // 问题描述

    @ManyToOne
    @JoinColumn(name = "activity_id")
    @Getter @Setter private Activity activity;
}
