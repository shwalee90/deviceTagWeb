package com.auxil.pump.repository;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.service.TbService;
import com.auxil.pump.template.RepositoryTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagBaseRepositoryTest extends RepositoryTestTemplate {

    @Autowired
    public SpringDataEquipRepository equipRepository;
    @Autowired
    public SpringDataTbMemoryRepository memoryRepository;

    @Test
    void findall() {
        //given

        //when, then
        List<TbEquipInfo> equipInfos = equipRepository.findAll();
    }





    @Test
    void findMemoryList(){

        // given
        long equip_id = 2;

        //when
        TbEquipInfo equipInfo = equipRepository.findByEquipid(equip_id);

        System.out.println(equipInfo.getEquip_type().getType_id());

        long typeId = equipInfo.getEquip_type().getType_id();


        List<TbMemoryInfo>  tmi =  memoryRepository.findMemoryByTypeId(typeId);

        tmi.stream().forEach(System.out::println);


    }



}
