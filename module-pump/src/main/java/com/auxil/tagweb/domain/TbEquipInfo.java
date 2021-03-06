package com.auxil.tagweb.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@ToString
public class TbEquipInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "EQUIP_ID")
    private long equipid;

    @Column(name = "EQUIP_ALIAS" , nullable = false , unique = true)
    private String equip_alias;

    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private TbEquipType equip_type;

    @Column (name = "IP" , nullable = false )
    private String ip;

    @Column (name = "PORT" , nullable = false )
    private int port;



}
