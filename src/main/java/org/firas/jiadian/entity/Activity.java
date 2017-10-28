package org.firas.jiadian.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.firas.common.helper.DateTimeHelper;

/**
 * 维修活动
 */
@Entity
@Table(name = "t_activity")
public class Activity extends org.firas.common.model.IdModel {

    public Activity() {}
    public Activity(int id) {
        setId(id);
    }

    public static final int MIN_YEAR = 2010;
    public static final int MAX_YEAR = 2099;
    @Column(nullable = false)
    @Getter @Setter private short year; // 学年

    public static final int MIN_SEMESTER = 1;
    public static final int MAX_SEMESTER = 3;
    @Column(nullable = false)
    @Getter @Setter private short semester; // 学期

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 20;
    @Column(nullable = false, length = NAME_MAX_LENGTH)
    @Getter @Setter private String name;

    @Column(nullable = false, name = "begin_time")
    @Getter @Setter private Date beginTime;

    @Column(nullable = false, name = "end_time")
    @Getter @Setter private Date endTime;

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> options = new HashMap<>(2, 1f);
        options.put("id", true);
        options.put("create_time_info", DateTimeHelper.getDateTimeFormatter());

        HashMap<String, Object> result = super.toMap(options);
        result.put("name", getName());
        result.put("year", getYear());
        result.put("semester", getSemester());
        result.put("begin_time_info",
                DateTimeHelper.getDateTimeFormatter().format(getBeginTime()));
        result.put("end_time_info",
                DateTimeHelper.getDateTimeFormatter().format(getEndTime()));
        return result;
    }
}
