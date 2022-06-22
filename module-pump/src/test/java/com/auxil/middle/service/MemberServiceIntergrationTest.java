package com.auxil.middle.service;

import com.auxil.middle.domain.Member;
import com.auxil.middle.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 테스트 후 롤백을 한다.
class MemberServiceIntergrationTest {

    @Autowired MemberService memberService ;
    @Autowired MemberRepository memberRepository ;
    @Autowired SecurityMemberService securityMemberService;


//    @Test
//    void loadUser(){
//
//
//
//        UserDetails ud =  securityMemberService.loadUserByUsername("shwalee");
//
//
//        System.out.println(ud.getUsername());
//        System.out.println(ud.getPassword());
//
//    }


    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("sspprr");
        member.setPassword("1234");


        // when
        Long saveId = memberService.join(member);

//        System.out.println("id = " +member.getId());
//        System.out.println("name= " +member.getName());

        //then
        Member findMem = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMem.getName());




    }
    

}