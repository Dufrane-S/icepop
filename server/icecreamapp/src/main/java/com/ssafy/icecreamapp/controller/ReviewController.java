package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.Review;
import com.ssafy.icecreamapp.model.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/addReview")
    @Operation(summary = "리뷰 작성",description = "member의 email을 넣고 orderId, rate, 후기 넣기")
    public Boolean addReview(@RequestBody Review review, @RequestParam String email) {
        review.setDate(System.currentTimeMillis());
        if (reviewService.addReview(review,email) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/getReviews")
    @Operation(summary = "리뷰 리스트", description = "parameter 없이 이용가능(모두 반환) isRecent : true일시 최근 5개 반환, email : member 기준 반환, orderId : orderId의 리뷰 반환")
    public List<Review> getReviews(@RequestParam(defaultValue = "false") boolean isRecent,
                                  @RequestParam(defaultValue = "0") String email,
                                  @RequestParam(defaultValue = "0") int orderId) {
        return reviewService.selectReviews(isRecent, email, orderId);
    }
}
