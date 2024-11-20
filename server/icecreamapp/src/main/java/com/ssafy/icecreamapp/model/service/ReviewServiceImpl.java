package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dao.ReviewDao;
import com.ssafy.icecreamapp.model.dto.Review;
import com.ssafy.icecreamapp.model.dto.ReviewCon;
import com.ssafy.icecreamapp.model.dto.request.InitReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewDao reviewDao;
    private final MemberDao memberDao;

    @Override
    public int addReview(InitReview initReview) {
        Review review = new Review(initReview);
        review.setMemberId(memberDao.selectByEmail(initReview.getEmail()).getId());
        return reviewDao.insertReview(review);
    }

    @Override
    public List<Review> selectReviews(ReviewCon reviewCon) {
        int memberId = memberDao.selectByEmail(reviewCon.getEmail()).getId();
        return reviewDao.selectReviewsByMemberId(reviewCon,memberId);
    }


}
