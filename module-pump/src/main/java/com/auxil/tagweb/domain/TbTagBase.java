package com.auxil.tagweb.domain;

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
    private long tagid;

    @Column(name= "TAGNAME" , nullable = false)
    private String tagname;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "EQUIPID" , nullable = false)
    private TbEquipInfo equipid;

    @Column(name = "MEMORY_DEVICE_NAME" , nullable = false)
    private String memorydevicename;

    @Column(name = "BLOCK_NO" , nullable = false)
    private int blockno;

    @Column(name = "ADDRESS" , nullable = false)
    private int address;

    @Column(name = "DATA_TYPE" , nullable = false)
    private String dataType;

    @Column(name = "TAG_ACCESS" , nullable = false)
    private String tagaccess;

    @Column(name = "DISPLAY_ADDRESS")
    private String displayaddress;


    public String getTagname() {
        return this.tagname;
    }

    public void setTagname(String tagname){
        this.tagname = tagname;
    }

}
