package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.request.InitMember;
import com.ssafy.icecreamapp.model.dto.request.LoginMember;
import com.ssafy.icecreamapp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "string : email<br>" +
            "string : pass를 받음<br>" +
            "로그인 성공시 MemberInfo를 바디로 반환 MemberEmail을 담은 쿠키를 반환, 로그인 실패시 401 null반환")
    public ResponseEntity<MemberInfo> login(@RequestBody LoginMember loginMember, HttpServletResponse response) throws UnsupportedEncodingException {
        Member selected = memberService.login(loginMember.getEmail(), loginMember.getPassword());
        if (selected != null) {
            Cookie cookie = new Cookie("loginId", URLEncoder.encode(selected.getEmail(), "utf-8"));
            cookie.setMaxAge(60 * 60 * 24 * 1); //1일
            response.addCookie(cookie);
            return ResponseEntity.ok(new MemberInfo(selected));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "string : name<br>" +
            "string : email<br>" +
            "string : password<br>" +
            "int : age<br>" +
            "int : gender -> 남자 1, 여자 2" +
            "<br>성공시 201 ")
    public ResponseEntity<String> join(@RequestBody InitMember initmember) {
        memberService.join(initmember);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/isUsed/{email}")
    @Operation(summary = "email중복체크", description = "string : email 전달시 사용 중이면 False 아니면 True")
    public ResponseEntity<Boolean> isUsed(@PathVariable String email) {
        if (memberService.isUsedEmail(email)) {
            return ResponseEntity.ok(Boolean.TRUE);
        }
        ResponseEntity.accepted().build();
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }

    @GetMapping("/info/{email}")
    @Operation(summary = "회원정보", description = "email을 넣으면 회원 정보를 반환")
    public ResponseEntity<MemberInfo> memberInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.infoByEmail(email));
    }
}
