package com.ssafy.icecreamapp.model.dto;

import com.ssafy.icecreamapp.model.dto.request.InitReview;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    int id;
    int orderId;
    int memberId;
    long date;
    float rate;
    String content;

    //왜 기본 생성자가 있어야 할까? mybatis가 결과값을 return할 때 먼저 기본 생성자를 이용해 객체를 생성하기 때문
    //내가 다른 생성자를 만들면 파라미터가 없는 기본 생성자가 아니라 내가 만든 생성자가 기본이 된다.
    public Review() {
    }


    public Review(InitReview initReview) {
        this.orderId = initReview.getOrderId();
        this.date = System.currentTimeMillis();
        this.rate = initReview.getRate();
        this.content = initReview.getContent();
    }
}
