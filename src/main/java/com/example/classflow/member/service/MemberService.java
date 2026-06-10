package com.example.classflow.member.service;

import com.example.classflow.member.Member;
import com.example.classflow.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long registerMember(Member member) {
        validateDuplicateEmail(member.getEmail());
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalStateException("Email already exists: " + email);
                });
    }

    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    /**
     * 로그인 처리
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 로그인 성공 시 Member 객체
     * @throws IllegalArgumentException 이메일 또는 비밀번호가 일치하지 않을 경우
     */
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return member;
    }
}
