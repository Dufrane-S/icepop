package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail {
    int id;
    int orderId;
    int productId;
    int quantity;
}
