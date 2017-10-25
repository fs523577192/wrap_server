package org.firas.jiadian.entity;

public class Activity extends org.firas.common.model. {

    @Id
    @Getter @Setter private int id;

    @Column
    @Getter @Setter private short year;

    @Column
    @Getter @Setter private short semester;

    @Column
    @Getter @Setter private String name;
}
