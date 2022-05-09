package com.auxil.pump.repository;

import com.auxil.pump.domain.TbEquipInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EquipRepositoryImpl implements EquipRepository{

    @PersistenceContext // #1
    private EntityManager em; // #2


    @Override
    public TbEquipInfo findById(Long id) {
        return em.find(TbEquipInfo.class, id);
    }
}
