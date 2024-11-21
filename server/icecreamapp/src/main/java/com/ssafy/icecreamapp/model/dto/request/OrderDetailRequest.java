package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequest {
    int productId;
    int quantity;
}
