package com.auxil.middle.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RealTimeModbusConnDTO {

    private String memoryName;
    private int address;

    public RealTimeModbusConnDTO(String memoryName, int address ){
        this.memoryName = memoryName;
        this.address = address;
    }




}
