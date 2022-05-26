package com.auxil.pump.service;

import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.repository.SpringDataEquipRepository;
import com.auxil.pump.repository.SpringDataTbMemoryRepository;
import com.auxil.pump.service.validator.EquipTypeFactory;
import com.auxil.pump.template.RepositoryTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class TagValidateTest extends RepositoryTestTemplate {

    @Autowired
    public SpringDataEquipRepository equipRepository;
    @Autowired
    public SpringDataTbMemoryRepository memoryRepository;

    @Autowired
    public EquipTypeFactory equipTypeFactory;



    @Test
    void findByMemoryTypeAndDeviceName(){

        // given
        long type_id = 2;

        //when



        List<TbMemoryInfo> memoryInfos = memoryRepository.findMemoryByTypeId(type_id);

        List<TbMemoryInfo> tmi = memoryInfos.stream().map(mem -> memoryRepository.findMemoryByTypeIdAndMemoryDeviceName(type_id, mem.getMemoryDeviceName())).collect(Collectors.toList());

        tmi.stream().forEach(System.out::println);






    }



}
