package org.firas.jiadian.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

/**
 * 家电成员
 */
@Entity
@Table(name = "t_member")
public class Member extends org.firas.user.model.WeixinUser {

    public static final String[] TEAM_SET = new String[] {
            "A", "B", "C", "D", "E"};
    @Column
    @Getter @Setter private String team;

    @Column(name = "school_idcode")
    @Getter @Setter private String schoolIdCode; // 学号

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Getter @Setter private Role role;
}
