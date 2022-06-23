package com.auxil.tagweb;

import com.auxil.tagweb.repository.*;
import com.auxil.tagweb.service.*;
import com.auxil.tagweb.repository.AuthRepository;
import com.auxil.tagweb.repository.MemberRepository;
import com.auxil.tagweb.repository.SpringDataTbMemoryRepository;
import com.auxil.tagweb.service.*;
import com.auxil.tagweb.service.validator.EquipTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;


    @Autowired
    public  SpringConfig(MemberRepository memberRepository , AuthRepository authRepository){
        this.memberRepository = memberRepository;
        this.authRepository = authRepository;
    }

    @Bean
    public MemberService memberService(){

        return new MemberService(memberRepository);
    }

    @Resource
    private AuthService authService;

    @Resource
    private EquipTypeFactory equipTypeFactory;

    @Resource
    private SpringDataTbMemoryRepository memoryRepository;


    @Resource
    private TbService tbService;

    @Resource
    private TestMod testMod;


    @Bean
    public SecurityMemberService securityMemberService(){

        return new SecurityMemberService(memberRepository);
    }
//    @Bean
//    public MemberRepository memberRepository(){
//
//
//      //  return new JdbcMemberRepository(dataSource);
//       return new JpaMemberRepository(em);
//    }

//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }



}
