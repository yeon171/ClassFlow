package com.example.classflow.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    public Member create(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @GetMapping
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}