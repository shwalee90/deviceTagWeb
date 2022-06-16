package com.auxil.pump.controller;

import com.auxil.pump.domain.ApiResponse;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.dto.RealTimeModbusConnDTO;
import com.auxil.pump.service.RealTimeService;
import com.auxil.pump.service.TbService;
import com.auxil.pump.service.TestMod;
import com.auxil.pump.service.validator.TagValidator;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@RestController
public class TagBaseController {

    @Lazy
    private final TbService tbService;
    @Lazy
    private final RealTimeService realTimeService;
    @Lazy
    private final TagValidator tagValidator;
    @Lazy
    private final TestMod testMod;

    @GetMapping ("/auth/equip")
    @ResponseBody
    public ResponseEntity<List<TbEquipInfo>> authEquip(HttpServletRequest request){


        List<TbEquipInfo> equipInfos = tbService.findEquipAll();


        return new ResponseEntity<List<TbEquipInfo>>(equipInfos , HttpStatus.OK) ;

    }


    @GetMapping ("/auth/tagCount/{equipid}")
    @ResponseBody
    public ResponseEntity authTagCount(@PathVariable("equipid") long equipid){

        TbEquipInfo equipInfo = tbService.findEquipById(equipid);

        long count = tbService.getTagCountByEquipid(equipInfo);

        HashMap<String,Long> countInfo = new HashMap<>();

        countInfo.put("tagCount", count);


        return new ResponseEntity(countInfo , HttpStatus.OK) ;

    }




    @GetMapping ("/auth/tagInfo/{equipid}")
    @ResponseBody
    public ResponseEntity authTagbase(@PathVariable("equipid") long equipid, HttpServletRequest request , Pageable pageable){

        TbEquipInfo equipInfo = tbService.findEquipById(equipid);

        Page<TbTagBase> tagPage = tbService.findTagById(equipInfo ,pageable);

        List<TbTagBase> tagInfos = tagPage.getContent();



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



    @PostMapping("/auth/realTimeTagValue/{equipid}")
    @ResponseBody
    public ResponseEntity realTimeValue(@PathVariable("equipid") long equipid, @RequestBody Map<String, Object>[] params ){

        TbEquipInfo equipInfo = tbService.findEquipById(equipid);

        List<RealTimeModbusConnDTO> tagList = new ArrayList<>();


        for (int i = 0 ; i < params.length ; i++ ){

            tagList.add(new RealTimeModbusConnDTO((String)params[i].get("memorydevicename"),(int)params[i].get("address")));
        }

        if(equipInfo != null){
          Map <String,Integer> addrValMap =  realTimeService.readModValue(equipInfo , tagList);




          Set<String> kSet = addrValMap.keySet();

//           동일내용 스트림으로 변환
//            for (int i = 0 ;  i < params.length ; i++){
//              for(String key :  kSet){
//                if(params[i].get("displayaddress").equals(key)){
//                    params[i].put("rtValue", addrValMap.get(key));
//                }
//              }
//          }

            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // for each 기존의 객체 이용 , map 새로운 객체 생성
            Arrays.stream(params).filter(fa -> kSet.stream().anyMatch(Predicate.isEqual(fa.get("displayaddress"))))
                    .forEach(fe -> fe.put("rtValue",addrValMap.get(fe.get("displayaddress"))) );

            Arrays.stream(params).forEach(fe -> fe.put("time" , now.format(formatter)));

            for (int i = 0 ;  i < params.length ; i++){
                System.out.println(params[i]);
            }
        }

        return new ResponseEntity(params, HttpStatus.valueOf(HttpStatus.OK.value()));
    }


    @PostMapping("/auth/insertTag")
    public ResponseEntity insertTag(@RequestBody HashMap<String, String> map ,TbTagBase tagBase, BindingResult bindingResult ){

        System.out.println(map);

        tagBase = new TbTagBase();

        TbEquipInfo  tbEquipInfo = tbService.findEquipById(Long.parseLong(map.get("id")));

        tagBase.setTagname(map.get("tagName"));
        tagBase.setDescription(map.get("description"));
        tagBase.setEquipid(tbEquipInfo);
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

            HashMap<String,String>[] hmArr = new HashMap[1];



            String rstMsg ="INSERT SUCCESS";
            HashMap<String,String> rstMap = new HashMap<>();
            rstMap.put("code", rstMsg);
            hmArr[0] = rstMap;

            response.setResult(hmArr);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.valueOf(HttpStatus.OK.value()));
        }

    }

}
