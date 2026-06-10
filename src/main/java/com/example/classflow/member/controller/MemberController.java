package com.example.classflow.member.controller;

import com.example.classflow.member.Member;
import com.example.classflow.member.dto.LoginRequestDto;
import com.example.classflow.member.dto.MemberRegistrationDto;
import com.example.classflow.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody MemberRegistrationDto registrationDto) {
        try {
            Member member = new Member();
            member.setName(registrationDto.getName());
            member.setEmail(registrationDto.getEmail());
            member.setPassword(registrationDto.getPassword()); // 실제 애플리케이션에서는 이 비밀번호를 해싱해야 합니다.

            Long memberId = memberService.registerMember(member);
            return new ResponseEntity<>("Member registered with ID: " + memberId, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering member: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login") // 로그인 API 추가
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Member loggedInMember = memberService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            // 실제 애플리케이션에서는 로그인 성공 후 JWT 토큰 등을 반환할 수 있습니다.
            // 여기서는 간단히 로그인 성공 메시지와 회원 정보를 반환합니다.
            return new ResponseEntity<>(loggedInMember, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        } catch (Exception e) {
            return new ResponseEntity<>("로그인 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> member = memberService.findMemberById(id);
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        Optional<Member> member = memberService.findMemberByEmail(email);
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
