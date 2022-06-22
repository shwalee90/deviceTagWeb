package com.auxil.middle.repository;

import com.auxil.middle.domain.Member;

import java.util.List;
import java.util.Optional;// null 처리 할때

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();





}
