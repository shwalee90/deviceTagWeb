package com.auxil.middle.repository;

import com.auxil.middle.domain.TbEquipInfo;
import com.auxil.middle.service.TbService;
import com.auxil.middle.template.RepositoryTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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





//



}
