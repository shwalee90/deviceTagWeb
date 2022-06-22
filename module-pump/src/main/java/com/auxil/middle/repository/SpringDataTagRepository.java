package com.auxil.middle.repository;

import com.auxil.middle.domain.TbEquipInfo;
import com.auxil.middle.domain.TbTagBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTagRepository extends JpaRepository<TbTagBase, Long> {

            Page<TbTagBase> findByEquipidOrderByTagidDesc(TbEquipInfo equip , Pageable pageable);


            List<TbTagBase> findByEquipid(TbEquipInfo equip);

            long countByEquipid(TbEquipInfo equip);

}

