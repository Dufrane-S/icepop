package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.exception.MyNoSuchElementException;
import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dao.ReviewDao;
import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.Review;
import com.ssafy.icecreamapp.model.dto.request.ReviewCon;
import com.ssafy.icecreamapp.model.dto.request.InitReview;
import com.ssafy.icecreamapp.model.dto.respond.ReviewInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final MemberDao memberDao;

    @Override
    public int addReview(InitReview initReview) {
        Review review = new Review(initReview);
        review.setMemberId(memberDao.selectByEmail(initReview.getEmail()).getId());
        return reviewDao.insertReview(review);
    }

    @Override
    public List<ReviewInfo> selectReviews(ReviewCon reviewCon) {
        Member member = new Member();

        if (reviewCon.getEmail() != "") {
            member = memberDao.selectByEmail(reviewCon.getEmail());
            if (member == null) throw new MyNoSuchElementException("이메일", reviewCon.getEmail());
        }
        List<ReviewInfo> reviewInfoList = new ArrayList<>();
        List<Review> reviewList = reviewDao.selectReviewsByMemberId(reviewCon, member.getId());

        for (Review review : reviewList) {
            if (member.getId() != review.getMemberId()) {
                member = memberDao.selectById(review.getMemberId());
            }
            ReviewInfo reviewInfo = new ReviewInfo(review);
            reviewInfo.setMemberEmail(member.getEmail());
            reviewInfo.setName(member.getName());
            reviewInfoList.add(reviewInfo);
        }
        return reviewInfoList;
    }

    @Override
    public int updateReview(InitReview initReview) {
        Review review = new Review(initReview);
        review.setDate(System.currentTimeMillis());
        return reviewDao.updateReview(review);
    }


}
