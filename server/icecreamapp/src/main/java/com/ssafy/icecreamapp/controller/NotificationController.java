package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.respond.NotificationInfo;
import com.ssafy.icecreamapp.service.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/notification")
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @GetMapping("/{email}")
    @Operation(summary = "알림 리스트 조회", description = "<b>string : email을 넣으면 알림 리스트 반환")
    public List<NotificationInfo>getNotiListByEmail(@PathVariable String email){
        return notificationService.getNotiByEmail(email);
    }
}
