package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.request.Token;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.request.InitMember;
import com.ssafy.icecreamapp.model.dto.request.LoginMember;
import com.ssafy.icecreamapp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "<b>string : email<br>" +
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
    @Operation(summary = "회원가입", description = "<b>string : name<br>" +
            "string : email<br>" +
            "string : password<br>" +
            "int : age<br>" +
            "int : gender -> 남자 1, 여자 2" +
            "<br>성공시 201</b> ")
    public ResponseEntity<String> join(@RequestBody InitMember initmember) {
        if(memberService.join(initmember)==1){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/isUsed/{email}")
    @Operation(summary = "email중복체크", description = "<b>string : email 전달시 사용 중이면 False 아니면 True</b>")
    public ResponseEntity<Boolean> isUsed(
            @Parameter(example = "test1")
            @PathVariable String email) {
        if (memberService.isUsedEmail(email)) {
            return ResponseEntity.ok(Boolean.TRUE);
        }
        ResponseEntity.accepted().build();
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }

    @GetMapping("/info/{email}")
    @Operation(summary = "회원정보", description = "<b>email을 넣으면 회원 정보를 반환</b>")
    public ResponseEntity<MemberInfo> memberInfo(
            @Parameter(example = "test1")
            @PathVariable String email) {
        return ResponseEntity.ok(memberService.infoByEmail(email));
    }

    @GetMapping("/grade-img/{img}")
    @Operation(summary = "회원 등급 이미지 반환", description = "<b>string : img 회원 정보의 img를 넣으면 img를 반환</b>")
    public ResponseEntity<byte[]> img(
            @Parameter(description = "회원 정보 조회의 img", example = "seed")
            @PathVariable String img
    ) throws IOException {
        Resource resource = new ClassPathResource("grade/" + img + ".png");
        InputStream inputStream = resource.getInputStream();
        byte[] imageBytes = inputStream.readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/setToken")
    public ResponseEntity<String> setToken(@RequestBody Token token){
        memberService.updateTokenByEmail(token);
        return ResponseEntity.ok("토큰 설정 완료 -> 회원 : " + token.getEmail());
    }
}

