package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderCon {
    boolean isRecent;
    String email;
    int orderId;
}

