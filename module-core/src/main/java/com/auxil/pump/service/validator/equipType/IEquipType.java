package com.auxil.pump.service.validator.equipType;

import com.auxil.pump.domain.TbTagBase;

import java.util.Map;

public interface IEquipType {

    Map<String, Object> validateMemoryNameAndType(TbTagBase tagBase);

    Map<String, Object> validateMemoryNameAndAddress(TbTagBase tagBase);
}
