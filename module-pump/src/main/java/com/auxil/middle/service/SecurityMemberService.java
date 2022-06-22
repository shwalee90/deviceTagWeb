package com.auxil.middle.service;

import com.auxil.middle.domain.Member;
import com.auxil.middle.domain.SecurityMember;
import com.auxil.middle.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityMemberService implements UserDetailsService {

    private MemberRepository memberRepository ;


    public SecurityMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호를 잘 못 입력 하셨습니다."));
        return new SecurityMember(member);
    }
}