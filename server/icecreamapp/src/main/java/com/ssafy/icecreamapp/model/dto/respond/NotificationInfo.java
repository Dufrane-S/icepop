package com.ssafy.icecreamapp.model.dto.respond;

import com.ssafy.icecreamapp.model.dto.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationInfo {
    int orderId;
    int type;
    long date;

    public NotificationInfo(Notification notification) {
        this.orderId = notification.getOrderId();
        this.type = notification.getType();
        this.date = notification.getDate();
    }
}
