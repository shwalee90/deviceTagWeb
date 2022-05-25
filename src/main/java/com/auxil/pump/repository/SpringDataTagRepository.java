package com.auxil.pump.repository;

import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbTagBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataTagRepository extends JpaRepository<TbTagBase, Long> {

            Page<TbTagBase> findByEquipidOrderByTagidDesc(TbEquipInfo equip , Pageable pageable);

            List<TbTagBase> findByEquipid(TbEquipInfo equip);

            long countByEquipid(TbEquipInfo equip);

}

