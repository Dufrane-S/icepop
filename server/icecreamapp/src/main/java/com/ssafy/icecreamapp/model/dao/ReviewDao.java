package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Review;import com.ssafy.icecreamapp.model.dto.request.ReviewCon;
import com.ssafy.icecreamapp.model.dto.respond.ReviewInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewDao {
    int insertReview(Review review);

    List<ReviewInfo>selectReviewsByMemberId(@Param("reviewCon") ReviewCon reviewCon, @Param("memberId") int memberId);

    int updateReview(@Param("review") Review review);
}
