package com.auxil.tagweb.service.validator.equipType;

import com.auxil.tagweb.domain.TbTagBase;

import java.util.HashMap;
import java.util.Map;

public interface IEquipType {

    Map<String, Object> validateMemoryNameAndType(TbTagBase tagBase);

    Map<String, Object> validateMemoryNameAndAddress(TbTagBase tagBase);

    Map<String, Object> validateMemoryNameAndValue(HashMap<String, String> hm);
}
