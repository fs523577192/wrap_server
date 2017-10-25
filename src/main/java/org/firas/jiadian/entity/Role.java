package org.firas.jiadian.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色
 */
@Entity
@Table(name = "t_jiadian_role")
public class Role extends org.firas.common.model.StatusModel {

    @Column
    @Getter @Setter private String name;
}
