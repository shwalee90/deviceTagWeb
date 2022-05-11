package com.auxil.pump.repository;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.TbEquipInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataEquipRepository extends JpaRepository<TbEquipInfo, Long> {

    List<TbEquipInfo> findAll();


}
