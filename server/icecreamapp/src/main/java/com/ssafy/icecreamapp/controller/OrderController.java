package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.OrderRequestResponse;
import com.ssafy.icecreamapp.model.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/makeOrder")
    @Operation(summary = "주문", description = "id, memberId,date, priceSum 제외 모두 채워줘야함 ")
    public Boolean makeOrder(@RequestBody OrderRequestResponse orderRequestResponse) {
        if(orderService.makeOrder(orderRequestResponse) != 0) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/byMember/{email}")
    public List<OrderRequestResponse> byMember(@PathVariable String email) {
        return orderService.selectOrderById(email,false);
    }

    @GetMapping("/recentByMember/{email}")
    public List<OrderRequestResponse> recenrByMember(@PathVariable String email){
        return orderService.selectOrderById(email, true);
    }
}
