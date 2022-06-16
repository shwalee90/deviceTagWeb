package com.auxil.pump.service.validator;

import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.repository.SpringDataTbMemoryRepository;
import com.auxil.pump.service.validator.equipType.IEquipType;
import com.auxil.pump.service.validator.equipType.ModbusValidate;
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
