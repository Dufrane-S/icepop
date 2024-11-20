package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dto.Review;

import java.util.List;

public interface ReviewService {

    int addReview(Review review, String email);
    List<Review> selectReviews(boolean isRecent, String email, int orderId);
}
