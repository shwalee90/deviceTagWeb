package com.auxil.pump.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@ToString
public class TbEquipType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TYPE_ID")
    private long type_id;

    @Column(name = "EQUIP_TYPE" , nullable = false )
    private String equip_type ;

    @Column(name = "DESCRIPTION" , nullable = true )
    private String description;




}
