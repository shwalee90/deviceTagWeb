package com.auxil.pump.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@ToString
public class TbTagBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long tag_id;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "Equipid" , nullable = false)
    private TbEquipInfo equip_id;

    @Column(name = "MemoryDeviceName" , nullable = false)
    private String memorydevicename;

    @Column(name = "BlockNo" , nullable = false)
    private int blockno;

    @Column(name = "Address" , nullable = false)
    private int address;

    @Column(name = "DataType" , nullable = false)
    private String datatype;

    @Column(name = "TagAccess" , nullable = false)
    private String tagaccess;

    @Column(name = "DisplayAddress")
    private String displayaddress;

    @Column(name = "UNISEQNO" , nullable = false)
    private int uniseqno;

}
