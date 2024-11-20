package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.OrderRequestResponse;
import com.ssafy.icecreamapp.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/makeOrder")
    public Boolean makeOrder(@RequestBody OrderRequestResponse orderRequestResponse, @RequestParam String email) {
        if (orderService.makeOrder(email, orderRequestResponse) != 0) {
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
