package com.ssafy.icecreamapp.model.dto;

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
}
