package com.auxil.pump.repository;

import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.template.RepositoryTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(EquipRepositoryImpl.class)
public class TagBaseRepositoryTest extends RepositoryTestTemplate {

    @Autowired
    protected EquipRepository equipRepository;

    @Sql("classpath:db/data.sql")
    @Test
    void findAlias() {
        //given
        Long id = 1L;
        String expectedName = "test";

        //when, then
        TbEquipInfo equipInfo = equipRepository.findById(id);
        assertEquals(equipInfo.getEquip_alias(),expectedName);
    }






}
