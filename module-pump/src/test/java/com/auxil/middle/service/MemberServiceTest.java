package com.auxil.middle.service;

import com.auxil.middle.domain.Member;
import com.auxil.middle.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
class MemberServiceTest {

    MemberService memberService ;
    MemoryMemberRepository memberRepository ;


    @BeforeEach
    public void beforeEach(){
        // di 의 예시이다..
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }


    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }


    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring");


        // when
        Long saveId = memberService.join(member);
        //then
        Member findMem = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMem.getName());




    }
    
    @Test
    public void memException(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        IllegalStateException e = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class
                , () -> memberService.join(member2));


        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

    }


    
    
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}