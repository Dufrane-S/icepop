package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.Review;
import com.ssafy.icecreamapp.model.dto.request.ReviewCon;
import com.ssafy.icecreamapp.model.dto.request.InitReview;
import com.ssafy.icecreamapp.model.dto.respond.ReviewInfo;
import com.ssafy.icecreamapp.model.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
    @Operation(summary = "리뷰 작성",description = "member의 email을 넣고 orderId, rate, 후기 넣기")
    public ResponseEntity<String> addReview(@RequestBody InitReview initReview) {
        if (reviewService.addReview(initReview) == 1) {
            return ResponseEntity.ok("리뷰 생성");
        }else{
            return new ResponseEntity<>("실패", HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/getReviews")
    @Operation(summary = "리뷰 리스트", description = "parameter 없이 이용가능(모두 반환) isRecent : true일시 최근 5개 반환, email : member 기준 반환, orderId : orderId의 리뷰 반환")
    public ResponseEntity<List<ReviewInfo>> getReviews(@RequestBody ReviewCon reviewCon) {
        return ResponseEntity.ok(reviewService.selectReviews(reviewCon));
    }
}
