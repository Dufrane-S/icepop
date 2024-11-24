package com.ssafy.icecreamapp.model.dto.respond;

import com.ssafy.icecreamapp.model.dto.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewInfo {
    int orderId;
    String email;
    long date;
    float rate;
    String name;
    String content;

    public ReviewInfo(Review review) {
        this.orderId = review.getOrderId();
        this.date = review.getDate();
        this.rate = review.getRate();
        this.content = review.getContent();;
    }
}
