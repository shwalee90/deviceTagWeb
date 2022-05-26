package com.auxil.pump.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "memberrole")
@ToString
public class MemberRole {
    public static final String USER = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rolename")
    private String roleName;

    @Column(name = "member_id")
    private long member_id;


    public MemberRole() {
    }

    public MemberRole(String roleName) {
        this.roleName = roleName;
    }
}