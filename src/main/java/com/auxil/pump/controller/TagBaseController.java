package com.auxil.pump.controller;

import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.service.TbService;
import com.auxil.pump.service.TestMod;
import com.auxil.pump.service.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagBaseController {

    @Lazy
    private final TbService tbService;
    @Lazy
    private final TestMod testMod;



    @GetMapping ("/auth/equip")
    @ResponseBody
    public ResponseEntity<List<TbEquipInfo>> authEquip(HttpServletRequest request){


        List<TbEquipInfo> equipInfos = tbService.findEquipAll();


        return new ResponseEntity<List<TbEquipInfo>>(equipInfos , HttpStatus.OK) ;

    }

    @GetMapping ("/auth/tagInfo/{equipid}")
    @ResponseBody
    public ResponseEntity<List<TbTagBase>> authTagbase(@PathVariable("equipid") long equipid, HttpServletRequest request){

        System.out.println(equipid);

        List<TbTagBase> tagInfos = tbService.findTagById(equipid);


        return new ResponseEntity<List<TbTagBase>>(tagInfos , HttpStatus.OK) ;

    }

    @GetMapping ("/auth/memoryList/{equipid}")
    @ResponseBody
    public ResponseEntity<List<TbMemoryInfo>> authMemoryList( @PathVariable("equipid") long equipid , HttpServletRequest request){

        TbEquipInfo equipInfo = tbService.findEquipById(equipid);

        System.out.println(equipInfo.getEquip_type().getEquip_type());

        long type_id = equipInfo.getEquip_type().getType_id();

        List<TbMemoryInfo> memoryInfos = tbService.findMemoryByTypeId(type_id);


    //    return new ResponseEntity<List<String>>(equipInfos , HttpStatus.OK) ;
        return  new ResponseEntity<List<TbMemoryInfo>>(memoryInfos , HttpStatus.OK) ;
    }



    @PostMapping("/auth/insertTag")
    public Object insertTag(@RequestBody HashMap<String, String> map , BindingResult bindingResult ){
        String rstMsg ="";

        TbTagBase tbTagBase = new TbTagBase();

        TbEquipInfo tbEquipInfo = new TbEquipInfo();
        tbEquipInfo.setEquipid(Long.parseLong(map.get("id")));

        tbTagBase.setTagname(map.get("tagName"));
        tbTagBase.setDescription(map.get("description"));
        tbTagBase.setEquip_id(tbEquipInfo);
        tbTagBase.setMemorydevicename(map.get("memoryName"));
        tbTagBase.setBlockno(0);
        tbTagBase.setAddress(Integer.parseInt(map.get("address")));
        tbTagBase.setDatatype(map.get("dataType"));
        tbTagBase.setTagaccess(map.get("access"));

        String displayAddr = map.get("memoryName")+map.get("address");
        tbTagBase.setDisplayaddress(displayAddr);


        TagValidator tagValidator = new TagValidator();
        tagValidator.validate(tbTagBase , bindingResult);

        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors();
        }


        tbService.insertTag(tbTagBase);


        return rstMsg;
    }


}
