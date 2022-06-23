package com.auxil.tagweb.repository;

import com.auxil.tagweb.domain.TbMemoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataTbMemoryRepository extends JpaRepository<TbMemoryInfo, Long> {


    List<TbMemoryInfo> findMemoryByTypeId(long type_id);
    TbMemoryInfo findMemoryByTypeIdAndMemoryDeviceName(long type_id , String memoryDeviceName);
}
