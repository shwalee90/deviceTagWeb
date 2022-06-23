package com.auxil.tagweb.repository;

import com.auxil.tagweb.domain.TbEquipInfo;
import com.auxil.tagweb.domain.TbTagBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTagRepository extends JpaRepository<TbTagBase, Long> {

            Page<TbTagBase> findByEquipidOrderByTagidDesc(TbEquipInfo equip , Pageable pageable);


            List<TbTagBase> findByEquipid(TbEquipInfo equip);

            long countByEquipid(TbEquipInfo equip);

}

