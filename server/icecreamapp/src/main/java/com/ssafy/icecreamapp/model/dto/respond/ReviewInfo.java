package com.ssafy.icecreamapp.model.dto.respond;

import com.ssafy.icecreamapp.model.dto.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewInfo {
    int orderId;
    String memberEmail;
    long date;
    float rate;
    String content;

    public ReviewInfo(Review review) {
        this.orderId = review.getOrderId();
        this.date = review.getDate();
        this.rate = review.getRate();
        this.content = review.getContent();;
    }
}
