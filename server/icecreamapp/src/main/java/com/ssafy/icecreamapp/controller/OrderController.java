package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.request.OrderCon;
import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;
import com.ssafy.icecreamapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/makeOrder")
    @Operation(summary = "주문", description = "id, memberId,date, priceSum 제외 모두 채워줘야함 ")
    public ResponseEntity<String> makeOrder(@RequestBody OrderRequest orderRequest) {
        if(orderService.makeOrder(orderRequest) != 0) {
            return new ResponseEntity<>("주문 성공", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("주문 실패",HttpStatus.BAD_GATEWAY);
        }
    }

    //db에 order와 orderdetail을 각각 조회하여 spring단에서 합치는 쓰레기 코드들
    /*@GetMapping("/byMember/{email}")
    @Operation(summary = "주문 목록 전부",description = "email만 넣으면 됨")
    public  ResponseEntity<List<OrderInfo>> byMember(@PathVariable String email) {
        return  ResponseEntity.ok(orderService.selectOrdersByEmail(email,false));
    }

    @GetMapping("/recentByMember/{email}")
    @Operation(summary = "주문목록 최근 5개",description = "eamil만 넣으면 됨")
    public ResponseEntity<List<OrderInfo>> recentByMember(@PathVariable String email){
        return ResponseEntity.ok(orderService.selectOrdersByEmail(email, true));
    }*/

    @PostMapping("/withmap")
    @Operation(summary = "주문 내역 조회", description = "memberId = 0 이면 멤버 조건 안걸기, orderId=0이면 orderId조건 안걸기 isRecent->최근 주문한 아이스크림 종류 10가지 주문 반환")
    public List<OrderInfo>orderInfoListWithCon(@RequestBody OrderCon orderCon){
        return orderService.selectOrdersWithCon(orderCon);
    }
}
