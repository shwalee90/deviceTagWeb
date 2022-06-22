package com.auxil.middle.service.validator.equipType;

import com.auxil.middle.domain.TbMemoryInfo;
import com.auxil.middle.domain.TbTagBase;
import com.auxil.middle.repository.SpringDataTbMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


// 장비별 메모리 타입 , 데이터 타입 , 어드레스 범위 체크
@Service
public class ModbusValidate implements IEquipType {

    private final SpringDataTbMemoryRepository memoryRepository;

    @Autowired
    public ModbusValidate(SpringDataTbMemoryRepository springDataTbMemoryRepository) {
        this.memoryRepository = springDataTbMemoryRepository;
    }

    @Override
    public Map<String, Object> validateMemoryNameAndType(TbTagBase tagBase){

        Map<String, Object> validateMap = new HashMap<>();

        boolean valCheck = true;
        String code = "";

        String memoryName = tagBase.getMemorydevicename();
        int address = tagBase.getAddress();
        String dataType = tagBase.getDataType();

        long type_id = tagBase.getEquipid().getEquip_type().getType_id();

        System.out.println(type_id);

        List<TbMemoryInfo> memoryInfos = memoryRepository.findMemoryByTypeId(type_id);

        List<TbMemoryInfo> tmi = memoryInfos.stream().map(mem -> memoryRepository.findMemoryByTypeIdAndMemoryDeviceName(type_id, mem.getMemoryDeviceName())).collect(Collectors.toList());

        Optional<TbMemoryInfo> matchName = tmi.stream().filter(tInfo -> memoryName.equalsIgnoreCase(tInfo.getMemoryDeviceName()))
                .findFirst();

        valCheck = matchName.stream().anyMatch(tInfo -> matchDataValue(dataType).equalsIgnoreCase(tInfo.getMemory_device_type()));

        if(!valCheck){
            if(memoryName.equalsIgnoreCase("C")){
                code ="메모리 이름과 데이터 타입이 맞지 않습니다.  Boolean을 선택해 주세요.";
            }else if(memoryName.equalsIgnoreCase("DI")){
                code ="메모리 이름과 데이터 타입이 맞지 않습니다.  Boolean을 선택해 주세요.";
            }else if(memoryName.equalsIgnoreCase("R")){
                code ="메모리 이름과 데이터 타입이 맞지 않습니다.  Short , Word , BCD16 중 하나를 선택해 주세요.";
            }else if(memoryName.equalsIgnoreCase("IR")){
                code ="메모리 이름과 데이터 타입이 맞지 않습니다.  Short , Word , BCD16 중 하나를 선택해 주세요.";
            }
        }
        else{
            code ="OK";
        }

        validateMap.put("code" , code );
        validateMap.put("valCheck" , valCheck);
        return validateMap;
    }

    private String matchDataValue(String dataType) {

        if(dataType.equalsIgnoreCase("Boolean")){
            return "BIT";
        }
        else if(dataType.equalsIgnoreCase("Short") || dataType.equalsIgnoreCase("Word") ||dataType.equalsIgnoreCase("BCD16")){
            return "WORD";
        }
        if(dataType.equalsIgnoreCase("BYTE") || dataType.equalsIgnoreCase("CHAR")){
            return "BYTE";
        }
        if(dataType.equalsIgnoreCase("Integer")||dataType.equalsIgnoreCase("DWORD")||dataType.equalsIgnoreCase("BCD32")||dataType.equalsIgnoreCase("FLOAT")){
            return "DWORD";
        }
        if(dataType.equalsIgnoreCase("Boolean")){
            return "BIT";
        }


        return null;

    }

    @Override
    public Map<String, Object> validateMemoryNameAndAddress(TbTagBase tagBase){

        Map<String, Object> validateMap = new HashMap<>();

        boolean valCheck = true;
        String code = "";

        String memoryName = tagBase.getMemorydevicename();
        int address = tagBase.getAddress();
        String dataType = tagBase.getDataType();

        if(address > 10000 || address < 1){
            code = "memory 영역과 주소 범위가 맞지 않습니다. 0 < ADDRESS < 10000";
            valCheck = false;
        }


        validateMap.put("code" , code );
        validateMap.put("valCheck" , valCheck);
        return validateMap;

    }

}
