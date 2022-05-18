package com.auxil.pump.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Configurable;

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

    @Column(name= "TAGNAME")
    private String tagname;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "EQUIPID" , nullable = false)
    private TbEquipInfo equip_id;

    @Column(name = "MEMORY_DEVICE_NAME" , nullable = false)
    private String memorydevicename;

    @Column(name = "BLOCK_NO" , nullable = false)
    private int blockno;

    @Column(name = "ADDRESS" , nullable = false)
    private int address;

    @Column(name = "DATA_TYPE" , nullable = false)
    private String datatype;

    @Column(name = "TAG_ACCESS" , nullable = false)
    private String tagaccess;

    @Column(name = "DISPLAY_ADDRESS")
    private String displayaddress;


}
