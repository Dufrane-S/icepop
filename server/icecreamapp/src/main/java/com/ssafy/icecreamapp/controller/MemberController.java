package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.MemberInfo;
import com.ssafy.icecreamapp.model.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "email, pass를 받음, 로그인 성공시 Member Dto를 반환, 로그인 실패시 null반환")
    public Member login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) throws UnsupportedEncodingException {
        Member selected = memberService.login(email, password);
        if (selected != null) {
            Cookie cookie = new Cookie("loginId", URLEncoder.encode(selected.getEmail(), "utf-8"));
            cookie.setMaxAge(60 * 60 * 24 * 1); //1일
            response.addCookie(cookie);
        }
        return selected;
    }

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "member dto를 받음, 성공시 true return 실패시 false\n" +
            "String name;\n" +
            "String email;\n" +
            "String password;\n" +
            "int gender; 1=남 2=여\n" +
            "int age;")
    public Boolean join(@RequestBody Member member) {
        if (memberService.join(member) == 1) return true;
        return false;
    }

    @GetMapping("/isUsed")
    @Operation(summary = "email중복체크", description = "eamil 전달시 사용 중이면 False 아니면 True")
    public Boolean isUsed(String email) {
        return memberService.isUsedId(email);
    }

    @GetMapping("/info")
    @Operation(summary = "회원정보", description = "email을 넣으면 회원 정보를 반환")
    public MemberInfo memberInfo(@RequestParam String email) {
        return memberService.info(email);
    }
}
