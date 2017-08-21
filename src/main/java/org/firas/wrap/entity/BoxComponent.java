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
@Table(name = "t_box_component")
@DynamicUpdate
@NoArgsConstructor
public class BoxComponent extends org.firas.common.model.IdModel {

    @Transient
    private static final long serialVersionUID = 1L;

    @Getter @Setter private Box box;
    @Getter @Setter private Component component;

    @Column
    @Getter @Setter private int number;
}
