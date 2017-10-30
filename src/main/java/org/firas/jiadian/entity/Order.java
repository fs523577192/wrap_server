package org.firas.jiadian.entity;

import java.util.Map;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;
import org.firas.common.helper.DateTimeHelper;

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

    @Column(nullable = false, length = 1)
    @Getter @Setter private String team; // 组别

    public static final int WORKER_MIN_LENGTH = 2;
    public static final int WORKER_MAX_LENGTH = 30;
    @Column(length = WORKER_MAX_LENGTH)
    @Getter @Setter private String worker; // 接修人

    @Column
    @Getter @Setter private String reason; // 问题描述

    @ManyToOne
    @JoinColumn(name = "activity_id")
    @Getter @Setter private Activity activity;

    @Override
    public String getStatusInfo() {
        switch (status) {
            case STATUS_DELETED:
                return "已删除";
            case STATUS_NORMAL:
                return "待维修";
            case STATUS_EDITING:
                return "维修中";
            case STATUS_FROZEN:
                return "维修失败";
            case STATUS_USED:
                return "维修成功";
        }
        return "未知";
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> options = new HashMap<>(3, 1f);
        options.put("id", true);
        options.put("status", true);
        options.put("create_time_info", DateTimeHelper.getDateTimeFormatter());
        HashMap<String, Object> result = super.toMap(options);
        result.put("name", getName());
        result.put("team", getTeam());
        result.put("owner", getOwner());
        result.put("mobile", getMobile());
        result.put("phone", getPhone());
        result.put("email", getEmail());
        result.put("school_idcode", getSchoolIdCode());
        result.put("worker", getWorker());
        result.put("reason", getReason());
        return result;
    }
}
