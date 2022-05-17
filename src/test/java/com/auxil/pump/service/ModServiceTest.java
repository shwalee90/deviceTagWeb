package com.auxil.pump.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ModServiceTest {
    @Autowired
    TestMod modServiceTest ;



    @Test
    void quickStart() {
        modServiceTest.quickStart();
    }
}