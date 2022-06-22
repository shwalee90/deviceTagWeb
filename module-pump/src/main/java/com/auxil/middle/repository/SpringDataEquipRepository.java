package com.auxil.middle.repository;

import com.auxil.middle.domain.TbEquipInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataEquipRepository extends JpaRepository<TbEquipInfo, Long> {

    List<TbEquipInfo> findAll();

    TbEquipInfo findByEquipid(long equip_id);
}
