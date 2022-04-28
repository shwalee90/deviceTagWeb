package com.auxil.pump.controller;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
public class MemberForm {
    private String name;
    private String password;


}
