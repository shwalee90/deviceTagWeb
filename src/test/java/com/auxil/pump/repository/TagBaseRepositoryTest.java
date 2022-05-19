package com.auxil.pump.repository;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.service.TbService;
import com.auxil.pump.template.RepositoryTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TagBaseRepositoryTest extends RepositoryTestTemplate {

    @Autowired
    public SpringDataEquipRepository equipRepository;
    @Autowired
    public SpringDataTbMemoryRepository memoryRepository;

    @Autowired
    SpringDataTagRepository tagRepository;

    @Autowired
    TbService tbService;


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


        List<TbTagBase> tagList = tagRepository.findByEquipid(equip_id);


        tagList.stream().forEach(System.out::println);


    }



}
