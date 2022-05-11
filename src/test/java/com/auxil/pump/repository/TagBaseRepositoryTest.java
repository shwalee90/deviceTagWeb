package com.auxil.pump.repository;

import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.service.TbService;
import com.auxil.pump.template.RepositoryTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagBaseRepositoryTest extends RepositoryTestTemplate {

    @Autowired
    public SpringDataEquipRepository equipRepository;

    @Test
    void findall() {
        //given

        //when, then
        List<TbEquipInfo> equipInfos = equipRepository.findAll();
    }






}
