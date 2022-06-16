package com.auxil.pump.repository;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> ,MemberRepository{
    @Override
    Optional<Member> findByName(String name);



}
