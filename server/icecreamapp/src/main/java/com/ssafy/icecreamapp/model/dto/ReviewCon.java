package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCon {
    String email;
    int orderId;
    Boolean isRecent;
}
