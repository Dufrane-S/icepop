package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dao.ReviewDao;
import com.ssafy.icecreamapp.model.dto.Review;
import com.ssafy.icecreamapp.model.dto.ReviewCon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewDao reviewDao;
    private final MemberDao memberDao;

    @Override
    public int addReview(Review review, String email) {
        review.setMemberId(memberDao.selectByEmail(email).getId());
        return reviewDao.insertReview(review);
    }

    @Override
    public List<Review> selectReviews(boolean isRecent, String email, int orderId) {
        int memberId = memberDao.selectByEmail(email).getId();
        return reviewDao.selectReviewsByMemberId(new ReviewCon(memberId, orderId, isRecent));
    }


}
