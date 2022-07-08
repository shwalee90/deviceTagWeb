package com.auxil.middle.service.validator.equipType;

import com.auxil.middle.domain.TbTagBase;

import java.util.HashMap;
import java.util.Map;

public interface IEquipType {

    Map<String, Object> validateMemoryNameAndType(TbTagBase tagBase);

    Map<String, Object> validateMemoryNameAndAddress(TbTagBase tagBase);

    Map<String, Object> validateMemoryNameAndValue(HashMap<String, String> hm);
}
