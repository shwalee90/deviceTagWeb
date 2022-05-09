package com.auxil.pump.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@ToString
public class TbMemoryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "MEMORY_DEVICE_NAME" , nullable = false)
    private String memory_device_name;

    @Column(name = "MEMORY_DEVICE_TYPE" , nullable = false)
    private String memory_device_type;

    @Column(name = "MULTIPLE_WRITE_FUNCTION" , nullable = false)
    private String multiple_write_function;

    @Column(name = "DESCRIPTION")
    private String description;


    @ManyToOne
    @JoinColumn(name = "EQUIP_TYPE")
    private TbEquipType equip_type;




}
