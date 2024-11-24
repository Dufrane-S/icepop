package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.request.ReviewCon;
import com.ssafy.icecreamapp.model.dto.request.InitReview;
import com.ssafy.icecreamapp.model.dto.respond.ReviewInfo;
import com.ssafy.icecreamapp.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/addReview")
    @Operation(summary = "리뷰 작성", description = "<b>string : email<br>" +
            "int : orderId 상품번호<br>" +
            "float : rate 별점 0~5 0.5 간격<br>" +
            "string : content 내용")
    public ResponseEntity<String> addReview(@RequestBody InitReview initReview) {
        //여기서 이상한 반환하지말고 Exception 날리기
        if (reviewService.addReview(initReview) == 1) {
            return ResponseEntity.ok("리뷰 생성");
        } else {
            return new ResponseEntity<>("실패", HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/getReviews")
    @Operation(summary = "리뷰 리스트", description = "<b>string : email <br>" +
            "int : orderId 주문번호<br>" +
            "boolean : recent true일시 최근 5개 LIMIT 5")
    public ResponseEntity<List<ReviewInfo>> getReviews(@RequestBody ReviewCon reviewCon) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.selectReviews(reviewCon));
    }

    @PostMapping("/updateReview")
    @Operation(summary = "리뷰 수정", description = "<b>string : email<br>" +
            "int : orderId 상품번호<br>" +
            "float : rate 별점 0~5 0.5 간격<br>" +
            "string : content 내용")
    public ResponseEntity<Integer> updateReview(@RequestBody InitReview initReview) {
        return ResponseEntity.ok(reviewService.updateReview(initReview));
    }
}
