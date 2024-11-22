package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.service.FirebaseCloudMessageServiceWithData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin("*")
public class TokenController {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);


    @Autowired
    FirebaseCloudMessageServiceWithData serviceWithData;

    

    @PostMapping("/sendDataMessageTo")
    public void sendDataMessageTo(String token, String title, String body) throws IOException {
        logger.info("sendMessageTo : token:{}, title:{}, body:{}", token, title, body);
        serviceWithData.sendDataMessageTo(token, title, body);
    }

    @PostMapping("/broadcast-data")
    public String broadCastData(String title, String body) throws IOException {
        logger.info("broadCast : title:{}, body:{}", title, body);

        return getMessage(serviceWithData.broadCastDataMessage(title, body));
    }

    private String getMessage(int cnt) {
        String msg = "";
        if(cnt > 0) {
            msg = "성공적으로 전송했습니다.";
        }else {
            msg = "전송할 대상이 없거나 메시지 전송에 실패했습니다. ";
        }
        return msg;
    }
}

