package com.auxil.pump.repository;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.RefreshToken;

import java.util.List;
import java.util.Optional;// null 처리 할때

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();





}
