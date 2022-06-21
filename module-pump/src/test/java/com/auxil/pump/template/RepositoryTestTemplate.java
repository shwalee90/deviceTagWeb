package com.auxil.pump.template;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@Transactional
@Disabled
public class RepositoryTestTemplate {
//    @Autowired
//    SomethingRepository somethingRepository;
//
//    @Test
//    void testName() throws Exception {
//        //given
//        Something something = new Something(1L, 2L, 300L);
//        Something insertedThing = somethingRepository.save(something);
//
//        //when
//        Something foundThing = somethingRepository.findById(insertedThing.getId());
//
//        //then
//        assertEquals(insertedThing, foundThing);
//    }
}