package org.firas.wrap.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Column;

import org.firas.common.helper.DateTimeHelper;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Entity
@Table(name = "t_box")
@DynamicUpdate
@NoArgsConstructor
public class Box extends org.firas.common.model.IdModel {

    @Transient
    private static final long serialVersionUID = 1L;

    public Box(String name) {
        setName(name);
    }

    public HashMap<String, Object> toMap() {
        Map<String, Object> options = new HashMap<String, Object>(1, 1f);
        options.put("id", true);
        options.put("create_time_info", DateTimeHelper.getDateTimeFormatter());
        HashMap<String, Object> map = toMap(options);
        map.put("name", name);
        return map;
    }

    public static final int NAME_MIN_LENGTH = 1, NAME_MAX_LENGTH = 100;
    @Column(nullable = false, length = NAME_MAX_LENGTH)
    @Getter private String name;
    public Box setName(String name) {
        this.name = null == name ? null : name.trim();
        if (null != this.name && this.name.length() <= 0) {
            this.name = null;
        }
        return this;
    }
}
