package com.auxil.middle.service;

import com.auxil.middle.domain.Member;
import com.auxil.middle.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository ;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


            /*
                회원가입
             */

    public Long join(Member member){

        // 같은 이름 X
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
    }

    /*
     전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }



}