package com.auxil.pump.service;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.repository.SpringDataEquipRepository;
import com.auxil.pump.repository.SpringDataTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbService {

    private final SpringDataEquipRepository equipRepository;
    private final SpringDataTagRepository tagRepository;

    public TbService(SpringDataEquipRepository equipRepository ,SpringDataTagRepository tagRepository) {
        this.equipRepository = equipRepository;
        this.tagRepository = tagRepository;
    }


    public List<TbEquipInfo> findEquipAll(){
        return equipRepository.findAll();
    }


    public List<TbTagBase> findTagById(long equipID) {return tagRepository.findByEquipid(equipID);
    }
}
