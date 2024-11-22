package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderCon {
    String email;
    int orderId;
    boolean isRecent;

    public OrderCon(String email, int orderId, boolean isRecent) {
        this.email = email;
        this.orderId = orderId;
        this.isRecent = isRecent;
    }
}

