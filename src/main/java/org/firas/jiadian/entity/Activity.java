package org.firas.jiadian.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 维修活动
 */
@Entity
@Table(name = "t_activity")
public class Activity extends org.firas.common.model.IdModel {

    @Column
    @Getter @Setter private short year; // 学年

    @Column
    @Getter @Setter private short semester; // 学期

    @Column
    @Getter @Setter private String name;

    @Column(name = "begin_time")
    @Getter @Setter private Date beginTime;

    @Column(name = "end_time")
    @Getter @Setter private Date endTime;
}
