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

    @Column(name = "TYPE_ID" , nullable = false , unique = true)
    private long type_id;




    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private TbEquipType tet;




}
