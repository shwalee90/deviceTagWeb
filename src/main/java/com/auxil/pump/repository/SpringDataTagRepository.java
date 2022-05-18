package com.auxil.pump.repository;

import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbTagBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataTagRepository extends JpaRepository<TbTagBase, Long> {

    @Query(value = "select tagname,id,address,block_no,data_type,description,display_address,memory_device_name," +
            "tag_access,uniseqno,equipid from tb_tag_base where equipid = :equipID " ,  nativeQuery = true )
            List<TbTagBase> findByEquipid(@Param(value="equipID") long equipID);



}