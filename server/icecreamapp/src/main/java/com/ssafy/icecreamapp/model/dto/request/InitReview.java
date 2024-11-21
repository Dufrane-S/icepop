package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InitReview {
    String email;
    int orderId;
    float rate;
    String content;
}
