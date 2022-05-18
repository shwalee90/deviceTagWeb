package com.auxil.pump.service.validator;

import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.service.validator.equipType.IEquipType;
import com.auxil.pump.service.validator.equipType.ModbusValidate;
import org.springframework.stereotype.Service;

@Service
public class EquipTypeFactory {
    public IEquipType createEquipType(String type ){

        IEquipType iEquipType = null;

        if("MODBUS".equals(type)){
            iEquipType = new ModbusValidate();
        }

        return iEquipType;
    };

}
