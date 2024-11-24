package com.ssafy.icecreamapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.icecreamapp.model.dto.request.KakaoPayFinal;
import com.ssafy.icecreamapp.model.dto.request.KakaoPayInit;
import com.ssafy.icecreamapp.model.dto.request.KakaoPayResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
@Slf4j
public class KakaoPayController {

    public final ObjectMapper objectMapper;
    public List<String>tidList=new ArrayList<>();
    private final WebClient webClient = WebClient.create();
    @GetMapping("/requestPay")
    @Operation(summary = "카카오페이 결제", description = "<b>현재 결제 성공 후 이동 주소를 localhost로 해두었기 때문에 pc 결제만 가능<br>" +
            "추후 싸피 pc ip 확인을 통해 조율해야<br>" +
            "process -> 앱에서 서버로 주문 요청 -> 스프링서버에서 카카오 서버로 정보 전송 후 redirect url 수령<br>" +
            "스프링 서버에서 앱으로 redirect url 전송 후 앱에서 webview로 redirect url 열기 <br>" +
            "카카오 결제 창 뜸 -> 결제하기 성공시 성공 후 이동 주소/?{pgtoken}으로 이동<br>" +
            "받은 pgtoken으로 스프링 서버에 다시 요청<br>" +
            "이 때 스프링 서버에서 주문을 db에 저장하고 fcm 발송")
    public KakaoPayResult kakaoPay() throws JsonProcessingException {

        KakaoPayInit dto = new KakaoPayInit();
        dto.setCid("TC0ONETIME");
        dto.setPartner_order_id("나중에 주문번호로 교체");
        dto.setPartner_user_id("나중에 member아디로 교체");
        dto.setItem_name("아이스크림");
        dto.setQuantity(1);
        dto.setTotal_amount(1000);
        dto.setTax_free_amount(100);
        dto.setApproval_url("http://localhost:8080/kakao/approved");
        dto.setFail_url("http://localhost:8080/failed");
        dto.setCancel_url("http://localhost:8080/canceld");
        String requestBody = objectMapper.writeValueAsString(dto);
        log.info(requestBody);
        JsonNode response = webClient.post()
                .uri("https://open-api.kakaopay.com/online/v1/payment/ready")
                .header("Authorization", "SECRET_KEY " + "")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        KakaoPayResult kakaoPayResult = new KakaoPayResult(response);
        tidList.add(response.path("tid").asText());
        log.info(kakaoPayResult.toString());
        return kakaoPayResult;
    }

    @GetMapping("/approved")
    public void approved(@RequestParam String pg_token) throws JsonProcessingException {
        KakaoPayFinal payFinal = new KakaoPayFinal();
        payFinal.setCid("TC0ONETIME");
        payFinal.setTid(tidList.get(0));
        tidList.clear();
        payFinal.setPartner_order_id("나중에 주문번호로 교체");
        payFinal.setPartner_user_id("나중에 member아디로 교체");
        payFinal.setPg_token(pg_token);

        String requestBody = objectMapper.writeValueAsString(payFinal);

        webClient.post()
                .uri("https://open-api.kakaopay.com/online/v1/payment/approve")
                .header("Authorization", "SECRET_KEY " + "")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

    }
}