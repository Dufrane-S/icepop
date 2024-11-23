package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification {
    int id;
    int memberId;
    int orderId;
    int type;
    long date;

    public Notification(int memberId, int orderId, int type) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.type = type;
        this.date = System.currentTimeMillis();
    }
}
