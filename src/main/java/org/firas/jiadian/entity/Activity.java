package org.firas.jiadian.entity;

import javax.persistence.Column;

/**
 * 维修单
 */
public class Order extends org.firas.common.model.IdModel {

    @Column
    @Getter @Setter private short year;

    @Column
    @Getter @Setter private short semester;

    @Column
    @Getter @Setter private String name;
}
