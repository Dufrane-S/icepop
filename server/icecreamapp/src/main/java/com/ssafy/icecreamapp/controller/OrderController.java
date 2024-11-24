package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dao.OrderDao;
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
    @Operation(summary = "주문", description = "<b>string :email<br>" +
            "int : spoon 숟가락 수<br>" +
            "int : dryice 드라이아이스 지속 시간<br>" +
            "int : forhere 매장 1, 포장 2 -> 0은 추후 조회 조건용으로 예비" +
            "List<OrderDetailRequest>details : OrderDetailRequest의 List" +
            "<OrderDetailRequest><br>" +
            "int : productId 상품번호<br>" +
            "int : quantity 수량<br>" +
            "int : priceSum 할인 받기 전 소비자가의 최종합 ex) 아이스크림류 id 1&2 2개 = 3500 + 3500<br>" +
            "int : discountSum 할인 받은 가격 ex) 7000-((3500 * 0.95)+(3500*0.9)) * 0.99= 589.75 590 원단위 절사(올림)<br>" +
            "int : resultSum 최종 결제 금액 ex) ((3500 * 0.95)+(3500 * 0.9)) * 0.99 = 6410.25 원단위 절사(버림)")
    public ResponseEntity<String> makeOrder(@RequestBody OrderRequest orderRequest) {
        if (orderService.makeOrder(orderRequest) != 0) {
            return new ResponseEntity<>("주문 성공", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("주문 실패", HttpStatus.BAD_GATEWAY);
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


//
    /*@PostMapping("/orderList")
    @Operation(summary = "주문 내역 조회", description = "email = '' 이면 멤버 조건 안걸기, orderId=0이면 orderId조건 안걸기 isRecent->최근 주문한 아이스크림 종류 10가지 주문 반환")
    public ResponseEntity<List<OrderInfo>>orderInfoListWithCon(@RequestBody OrderCon orderCon){
        return ResponseEntity.ok(orderService.selectOrdersWithCon(orderCon));
    }*/

    @PostMapping("/orderList")
    @Operation(summary = "주문 내역 조회", description = "<b>string : email ''일 경우 조건 X<br>" +
            "int : order_id 0일 경우 조건 X<br>" +
            "boolean : recent true일 경우 최근 주문 목록 10개만 조회 LIMIT 10" +
            "<br>단건 조회 할 때 -> email= `` , orderId=1, recent=false or ture 상관 없음")
    public ResponseEntity<List<OrderInfo>> orderList(@RequestBody OrderCon orderCon) {
        return ResponseEntity.ok(orderService.selectOrdersWithCon2(orderCon));
    }
}
