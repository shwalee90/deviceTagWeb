package com.auxil.pump.service;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.repository.SpringDataEquipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbService {

    private final SpringDataEquipRepository equipRepository;


    public TbService(SpringDataEquipRepository equipRepository) {
        this.equipRepository = equipRepository;
    }


    public List<TbEquipInfo> findAll(){
        return equipRepository.findAll();
    }




}
