package com.auxil.pump.controller;

import com.auxil.pump.domain.ApiResponse;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.service.TbService;
import com.auxil.pump.service.TestMod;
import com.auxil.pump.service.validator.TagValidator;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagBaseController {

    @Lazy
    private final TbService tbService;
    @Lazy
    private final TestMod testMod;
    @Lazy
    private final TagValidator tagValidator;


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
    public ResponseEntity insertTag(@RequestBody HashMap<String, String> map ,TbTagBase tagBase, BindingResult bindingResult ){

        System.out.println(map);


        tagBase = new TbTagBase();

        TbEquipInfo  tbEquipInfo = tbService.findEquipById(Long.parseLong(map.get("id")));

        tagBase.setTagname(map.get("tagName"));
        tagBase.setDescription(map.get("description"));
        tagBase.setEquip_id(tbEquipInfo);
        tagBase.setMemorydevicename(map.get("memoryName"));
        tagBase.setBlockno(0);
        try{
            tagBase.setAddress(Integer.parseInt(map.get("address")));
        }catch (NumberFormatException ne){
            bindingResult.rejectValue("address", "address 에 숫자를 써주세요.");
        }
        tagBase.setDataType(map.get("dataType"));
        tagBase.setTagaccess(map.get("access"));

        String displayAddr = map.get("memoryName")+map.get("address");
        tagBase.setDisplayaddress(displayAddr);
        tagValidator.validate(tagBase , bindingResult);
        if(bindingResult.hasErrors()){
            ApiResponse response = new ApiResponse();
            response.setResult(bindingResult.getAllErrors());
            return new ResponseEntity<ApiResponse>(response, HttpStatus.valueOf(HttpStatus.OK.value()));
        }else{
            tbService.insertTag(tagBase);
            ApiResponse response = new ApiResponse();
            String rstMsg ="INSERT SUCCESS";
            HashMap<String,String> rstMap = new HashMap<>();
            rstMap.put("result", rstMsg);
            response.setResult(rstMap);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.valueOf(HttpStatus.OK.value()));
        }

    }

}
