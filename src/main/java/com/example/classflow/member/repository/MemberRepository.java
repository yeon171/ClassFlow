package com.example.classflow.member.repository;

import com.example.classflow.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository
        extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}