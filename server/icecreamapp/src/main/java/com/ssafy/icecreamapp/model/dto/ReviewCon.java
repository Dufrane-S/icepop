package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCon {
    int memberId;
    int orderId;
    Boolean isRecent;

    public ReviewCon(int memberId, int orderId, Boolean isRecent) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.isRecent = isRecent;
    }
}
