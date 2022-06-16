package com.auxil.pump.service;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbMemoryInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.repository.SpringDataEquipRepository;
import com.auxil.pump.repository.SpringDataTagRepository;
import com.auxil.pump.repository.SpringDataTbMemoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TbService {

    private final SpringDataEquipRepository equipRepository;
    private final SpringDataTagRepository tagRepository;
    private final SpringDataTbMemoryRepository memoryRepository;

    public TbService(SpringDataEquipRepository equipRepository ,SpringDataTagRepository tagRepository,
            SpringDataTbMemoryRepository memoryRepsitory) {
        this.equipRepository = equipRepository;
        this.tagRepository = tagRepository;
        this.memoryRepository = memoryRepsitory;
    }

    public List<TbEquipInfo> findEquipAll(){
        return equipRepository.findAll();
    }


    public Page<TbTagBase> findTagById(TbEquipInfo equip , Pageable pageable) {return tagRepository.findByEquipidOrderByTagidDesc(equip , pageable);
    };

    public List<TbTagBase> findTagForBatch() {return tagRepository.findAll();
    };


    public TbEquipInfo findEquipById(long equipID) {return  equipRepository.findByEquipid(equipID);
    };

    public List<TbMemoryInfo> findMemoryByTypeId(long type_id) {return memoryRepository.findMemoryByTypeId(type_id);
    };

    public TbMemoryInfo findMemoryByTypeIdAndMemoryDeviceName(long type_id , String memoryDeviceName){
        return memoryRepository.findMemoryByTypeIdAndMemoryDeviceName(type_id, memoryDeviceName);
    }


    public void insertTag(TbTagBase tbTagBase) {tagRepository.save(tbTagBase);
    };

    public long getTagCountByEquipid(TbEquipInfo equipInfo) {
       return tagRepository.countByEquipid(equipInfo);
    };


}
