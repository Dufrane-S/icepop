package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitReview {
    int orderId;
    String email;
    float rate;
    String content;
}
