package com.auxil.middle.service.validator;

import com.auxil.middle.domain.TbEquipInfo;
import com.auxil.middle.domain.TbTagBase;
import com.auxil.middle.repository.SpringDataTagRepository;
import com.auxil.middle.service.TbService;
import com.auxil.middle.service.validator.equipType.IEquipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.List;

@Component
public class TagWriteValidator implements Validator {

    @Autowired
    SpringDataTagRepository tagRepository;

    @Autowired
    TbService tbService;

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

        HashMap<String, String> hm = (HashMap<String, String>) target;

        TbEquipInfo tbEquipInfo = tbService.findEquipById(Long.parseLong(hm.get("id")));

        String equipType = tbEquipInfo.getEquip_type().getEquip_type();

        factoryEquipType = equipTypeFactory.createEquipType(equipType);


        if((Boolean) factoryEquipType.validateMemoryNameAndValue(hm).get("valCheck") == false){
            errors.rejectValue("address", (String)factoryEquipType.validateMemoryNameAndValue(hm).get("code"));
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
