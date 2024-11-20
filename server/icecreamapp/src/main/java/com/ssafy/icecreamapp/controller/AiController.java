package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.Icecream;
import com.ssafy.icecreamapp.model.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;


    @GetMapping("/recommand/{email}")
    @Operation(summary = "ai추천", description = "email을 입력시 사용자 정보 및 주문 내역을 바탕으로 추천한 물품을 5개 리스트로 반환")
    public List<Icecream> aiRecommand(@PathVariable String email){
        return aiService.aiRecommand(email);
    }
}
