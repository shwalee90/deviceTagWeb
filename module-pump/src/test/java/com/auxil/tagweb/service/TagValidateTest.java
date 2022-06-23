package com.auxil.tagweb.service;

import com.auxil.tagweb.domain.TbMemoryInfo;
import com.auxil.tagweb.repository.SpringDataEquipRepository;
import com.auxil.tagweb.repository.SpringDataTbMemoryRepository;
import com.auxil.tagweb.service.validator.EquipTypeFactory;
import com.auxil.tagweb.template.RepositoryTestTemplate;
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
