package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dto.Review;
import com.ssafy.icecreamapp.model.dto.ReviewCon;
import com.ssafy.icecreamapp.model.dto.request.InitReview;

import java.util.List;

public interface ReviewService {

    int addReview(InitReview initReview);
    List<Review> selectReviews(ReviewCon reviewCon);
}
