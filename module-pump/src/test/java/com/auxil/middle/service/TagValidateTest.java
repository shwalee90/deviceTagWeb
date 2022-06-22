package com.auxil.middle.service;

import com.auxil.middle.domain.TbMemoryInfo;
import com.auxil.middle.repository.SpringDataEquipRepository;
import com.auxil.middle.repository.SpringDataTbMemoryRepository;
import com.auxil.middle.service.validator.EquipTypeFactory;
import com.auxil.middle.template.RepositoryTestTemplate;
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
