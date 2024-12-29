package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.service.FirebaseCloudMessageServiceWithData;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@Hidden
@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class TokenController {


    private final FirebaseCloudMessageServiceWithData serviceWithData;

//  알림 테스트용
    @PostMapping("/sendDataMessageTo")
    public void sendDataMessageTo(String token, String title, String body) throws IOException {
        log.info("sendMessageTo : token:{}, title:{}, body:{}", token, title, body);
        serviceWithData.sendDataMessageTo(token, title, body);
    }

}

