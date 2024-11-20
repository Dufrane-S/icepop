package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Review;
import com.ssafy.icecreamapp.model.dto.ReviewCon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewDao {
    int insertReview(Review review);

    List<Review>selectReviewsByMemberId(ReviewCon reviewCon);

}
