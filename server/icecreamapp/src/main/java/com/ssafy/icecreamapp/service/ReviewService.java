package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.model.dto.request.ReviewCon;
import com.ssafy.icecreamapp.model.dto.request.InitReview;
import com.ssafy.icecreamapp.model.dto.respond.ReviewInfo;

import java.util.List;

public interface ReviewService {

    int addReview(InitReview initReview);
    List<ReviewInfo> selectReviews(ReviewCon reviewCon);
    int updateReview(InitReview initReview);
}
