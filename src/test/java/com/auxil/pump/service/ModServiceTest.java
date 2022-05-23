package com.auxil.pump.service;

import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.dto.RealTimeModbusConnDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ModServiceTest {
    @Autowired
    TestMod modServiceTest ;

    @Autowired
    RealTimeService realTimeService ;

    @Autowired
    TbService tbService;

    @Test
    void quickStart() {

        modServiceTest.quickStart();
    }

    @Test
    void readValue() {

        TbEquipInfo tbEquipInfo = tbService.findEquipById(2);

        RealTimeModbusConnDTO realTimeModbusConnDTO = new RealTimeModbusConnDTO("C" , 1);
        List<RealTimeModbusConnDTO> list = new ArrayList<>();

        list.add(realTimeModbusConnDTO);
        realTimeService.readModValue(tbEquipInfo ,list );



    }



}