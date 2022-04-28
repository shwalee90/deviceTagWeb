package com.auxil.pump;

import com.auxil.pump.aop.TimeTraceAop;
import com.auxil.pump.repository.*;
import com.auxil.pump.security.AuthenticationEntryPointHandler;
import com.auxil.pump.security.JwtTokenProvider;
import com.auxil.pump.security.WebAccessDeniedHandler;
import com.auxil.pump.service.AuthService;
import com.auxil.pump.service.MemberService;
import com.auxil.pump.service.SecurityMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

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
