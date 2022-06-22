package com.auxil.middle.template;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
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