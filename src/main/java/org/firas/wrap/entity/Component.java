package org.firas.wrap.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Column;

import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "t_component")
@DynamicUpdate
@NoArgsConstructor
public class Component extends org.firas.common.model.IdModel {

    @Transient
    private static final long serialVersionUID = 1L;

    public Component(String name) {
        setName(name);
    }

    public static final int NAME_MIN_LENGTH = 1, NAME_MAX_LENGTH = 100;
    @Column(nullable = false, length = NAME_MAX_LENGTH)
    @Getter private String name;
    public Component setName(String name) {
        this.name = null == name ? null : name.trim();
        if (null != this.name && this.name.length() <= 0) {
            this.name = null;
        }
        return this;
    }

    public static final int NUMBER_MIN = 0, NUMBER_MAX = Integer.MAX_VALUE;
    @Column(nullable = false)
    @Getter @Setter private int number = 0;
}
