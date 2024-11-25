package com.ssafy.icecreamapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.icecreamapp.model.dto.request.KakaoPayFinal;
import com.ssafy.icecreamapp.model.dto.KakaoPay;
import com.ssafy.icecreamapp.model.dto.request.KakaoPayInitRequest;
import com.ssafy.icecreamapp.model.dto.request.KakaoPayResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
@Slf4j
public class KakaoPayController {

    public final ObjectMapper objectMapper;
//    static private final Map<String,KakaoPay>kakaoPayMap= new HashMap<>();
    static private final List<KakaoPay>kakaoPayList = new ArrayList<>();
    static private final String KAKAO_API_KEY="";
    private final WebClient webClient = WebClient.create();
    @PostMapping("/requestPay")
    @Operation(summary = "카카오페이 결제", description = "<b>현재 결제 성공 후 이동 주소를 localhost로 해두었기 때문에 pc 결제만 가능<br>" +
            "추후 싸피 pc ip 확인을 통해 조율해야<br>" +
            "process -> 앱에서 서버로 주문 요청 -> 스프링서버에서 카카오 서버로 정보 전송 후 redirect url 수령<br>" +
            "스프링 서버에서 앱으로 redirect url 전송 후 앱에서 webview로 redirect url 열기 <br>" +
            "카카오 결제 창 뜸 -> 결제하기 성공시 성공 후 이동 주소/?{pgtoken}으로 이동<br>" +
            "받은 pgtoken으로 스프링 서버에 다시 요청<br>" +
            "이 때 스프링 서버에서 주문을 db에 저장하고 fcm 발송")
    public KakaoPayResult kakaoPay(@RequestBody KakaoPayInitRequest kakaoPayInitRequest) throws JsonProcessingException {


        KakaoPay kakaoPay = new KakaoPay(kakaoPayInitRequest);

        //order 넣고 orderId 받아서 저장?
        kakaoPay.setPartner_order_id("임시 주문 번호");
        String requestBody = objectMapper.writeValueAsString(kakaoPay);
        log.info(requestBody);
        JsonNode response = webClient.post()
                .uri("https://open-api.kakaopay.com/online/v1/payment/ready")
                .header("Authorization", "SECRET_KEY " + KAKAO_API_KEY)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        KakaoPayResult kakaoPayResult = new KakaoPayResult(response);
        kakaoPay.setTid(response.path("tid").asText());
//        kakaoPayMap.put(kakaoPay.getPartner_user_id(),kakaoPay);
        kakaoPayList.add(kakaoPay);
        log.info(kakaoPayResult.toString());
        return kakaoPayResult;
    }

    @GetMapping("/approved")
    public void approved(@RequestParam String pg_token) throws JsonProcessingException {
//      header가 없어서 현재 기술로서는 user구분이 불가능함 한 번에 하나의 주문만 처리한다고 가정해야햐함
        KakaoPay kakaoPay = kakaoPayList.get(0);
        KakaoPayFinal payFinal = new KakaoPayFinal(kakaoPay);

        payFinal.setPg_token(pg_token);

        kakaoPayList.clear();
        String requestBody = objectMapper.writeValueAsString(payFinal);
        log.info("kakao pay approved");
        log.info(requestBody);
        webClient.post()
                .uri("https://open-api.kakaopay.com/online/v1/payment/approve")
                .header("Authorization", "SECRET_KEY " + KAKAO_API_KEY)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

    }
    
    @GetMapping("/failed")
    public void failed(){
        kakaoPayList.clear();
    }
    @GetMapping("/canceled")
    public void canceled(){
        kakaoPayList.clear();
    }
}