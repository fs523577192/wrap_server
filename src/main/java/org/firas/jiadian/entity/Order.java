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
public class Order extends org.firas.common.model.IdModel {

    @Column(length = 11)
    @Getter @Setter private String mobile; // 物主手机

    public static final int PHONE_MAX_LENGTH = 20;
    @Column(length = PHONE_MAX_LENGTH)
    @Getter @Setter private String phone; // 物主固话

    public static final int EMAIL_MAX_LENGTH = 60;
    @Column(length = EMAIL_MAX_LENGTH)
    @Getter @Setter private String email; // 物主email

    public static final int SCHOOL_IDCODE_MIN_LENGTH = 6;
    public static final int SCHOOL_IDCODE_MAX_LENGTH = 15;
    @Column(name = "school_idcode")
    @Getter @Setter private String schoolIdCode; // 物主学号

    public static final int OWNER_MIN_LENGTH = 2;
    public static final int OWNER_MAX_LENGTH = 30;
    @Column(nullable = false, length = OWNER_MAX_LENGTH)
    @Getter @Setter private String owner; // 物主

    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 100;
    @Column(nullable = false, length = NAME_MAX_LENGTH)
    @Getter @Setter private String name; // 物品名称

    public static final int WORKER_MIN_LENGTH = 2;
    public static final int WORKER_MAX_LENGTH = 30;
    @Column(length = WORKER_MAX_LENGTH)
    @Getter @Setter private String worker; // 接修人

    @Column
    @Getter @Setter private String reason; // 问题描述

    @ManyToOne
    @JoinColumn(name = "activity_id")
    @Getter @Setter private Activity activity;
}
