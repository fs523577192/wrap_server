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
        this.name = name;
    }

    @Column(nullable = false, length = 100)
    @Getter @Setter private String name;
}
