package com.auxil.middle.service.validator;

import com.auxil.middle.repository.SpringDataTbMemoryRepository;
import com.auxil.middle.service.validator.equipType.IEquipType;
import com.auxil.middle.service.validator.equipType.ModbusValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipTypeFactory {
    @Autowired
    SpringDataTbMemoryRepository springDataTbMemoryRepository;

    public IEquipType createEquipType(String type ){

        IEquipType iEquipType = null;



        if("MODBUS".equals(type)){
            iEquipType = new ModbusValidate(springDataTbMemoryRepository);
        }

        return iEquipType;
    };

}
