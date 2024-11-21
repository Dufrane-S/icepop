package com.ssafy.icecreamapp.model.dto;

import com.ssafy.icecreamapp.model.dto.request.OrderDetailRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    int id;
    int orderId;
    int productId;
    int quantity;

    public OrderDetail(OrderDetailRequest orderDetailRequest) {
        this.productId = orderDetailRequest.getProductId();
        this.quantity = orderDetailRequest.getQuantity();
    }
}
