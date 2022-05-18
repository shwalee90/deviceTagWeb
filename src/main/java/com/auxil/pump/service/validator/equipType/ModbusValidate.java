package com.auxil.pump.service.validator.equipType;

import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.service.TbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// 장비별 메모리 타입 , 데이터 타입 , 어드레스 범위 체크
public class ModbusValidate implements IEquipType {


    @Autowired
    TbService tbService;


    @Override
    public Map<String, Object> validateMemoryNameAndType(TbTagBase tagBase){

        Map<String, Object> validateMap = new HashMap<>();

        boolean valCheck = true;
        String code = "";


        String memoryName = tagBase.getMemorydevicename();
        int address = tagBase.getAddress();
        String dataType = tagBase.getDatatype();

        long type_id = tagBase.getEquip_id().getEquip_type().getType_id();

        List<TbMemoryInfo> memoryInfos = tbService.findMemoryByTypeId(type_id);

        List<TbMemoryInfo> tmi = memoryInfos.stream().map(mem -> tbService.findMemoryByTypeIdAndMemoryDeviceName(type_id, mem.getMemoryDeviceName())).collect(Collectors.toList());


        validateMap.put("code" , code );
        validateMap.put("valCheck" , valCheck);
        return validateMap;


    }

    @Override
    public Map<String, Object> validateMemoryNameAndAddress(TbTagBase tagBase){

        Map<String, Object> validateMap = new HashMap<>();

        boolean valCheck = true;
        String code = "";

        String memoryName = tagBase.getMemorydevicename();
        int address = tagBase.getAddress();
        String dataType = tagBase.getDatatype();

        if(address > 10000 || address < 1){
            code = "memory 영역과 주소 범위가 맞지 않습니다. 0< ADDRESS < 10000";
            valCheck = false;
        }


        validateMap.put("code" , code );
        validateMap.put("valCheck" , valCheck);
        return validateMap;


    }



}
