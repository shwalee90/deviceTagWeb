package com.auxil.pump.repository;

import com.auxil.pump.domain.TbTagBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataTagRepository extends JpaRepository<TbTagBase, Long> {

            List findByEquipid(@Param(value="equipID") long equipID);


        }