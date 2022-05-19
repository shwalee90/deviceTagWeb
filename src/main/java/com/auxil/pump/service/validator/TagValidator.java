package com.auxil.pump.service.validator;

import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.repository.SpringDataTagRepository;
import com.auxil.pump.service.validator.equipType.IEquipType;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.List;

@Component
public class TagValidator implements Validator {

    @Autowired
    SpringDataTagRepository tagRepository;

    @Autowired
    EquipTypeFactory equipTypeFactory;

    @Autowired
    IEquipType factoryEquipType;



    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(TbTagBase.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TbTagBase tagBase = (TbTagBase)target;

        List<TbTagBase> tagList = tagRepository.findByEquipid(tagBase.getEquip_id().getEquipid());
        String equipType = tagBase.getEquip_id().getEquip_type().getEquip_type();

        factoryEquipType = equipTypeFactory.createEquipType(equipType);


        if((Boolean) factoryEquipType.validateMemoryNameAndAddress(tagBase).get("valCheck") == false){
            errors.rejectValue("address", (String)factoryEquipType.validateMemoryNameAndAddress(tagBase).get("code"));
        }
        if((Boolean)factoryEquipType.validateMemoryNameAndType(tagBase).get("valCheck") == false){
            errors.rejectValue("dataType", (String)factoryEquipType.validateMemoryNameAndType(tagBase).get("code"));
        }

        if(tagList.stream().anyMatch(tag -> tag.getTagname().equals(tagBase.getTagname()))){
           errors.rejectValue("tagname", "동일 설비 내에서 TAG 이름 중복");
        }

        if(!StringUtils.hasText(tagBase.getTagname())){
            errors.rejectValue("tagname", "TAG 이름이 입력되지 않았습니다.");
        }

        if(!StringUtils.hasText(Integer.toString(tagBase.getAddress()))){
            errors.rejectValue("address", "address가 입력되지 않았습니다.");
        }

        if(!StringUtils.hasText(tagBase.getDataType())){
            errors.rejectValue("datatype", "dataType가 입력되지 않았습니다.");
        }


    }

//
//    public HashMap<String , Object> validateForInsert(TbTagBase tbTagBase) {
//        HashMap<String , Object> validateMap = new HashMap<>();
//
//        boolean failValidate = false;
//        String rstMsg = "";
//        List<TbTagBase> tagList = tagRepository.findByEquipid(tbTagBase.getEquip_id().getEquipid());
//        String equipType = tbTagBase.getEquip_id().getEquip_type().getEquip_type();
//        failValidate = tagList.stream().anyMatch(tag -> tag.getTagname().equals(tbTagBase.getTagname()));
//
//
//
//
//        if(failValidate == false){
//
//
//        }else{
//            rstMsg = "TAG NAME Duplicate";
//            validateMap.put("failValidate",failValidate);
//            validateMap.put("rstMsg" , rstMsg);
//            return validateMap;
//        }
//
//
//        rstMsg = "INSERT SUCCESS!";
//        validateMap.put("failValidate",failValidate);
//        validateMap.put("rstMsg" , rstMsg);
//        return validateMap;
//    }
//
//


}
