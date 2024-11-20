package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dto.Order;
import com.ssafy.icecreamapp.model.dto.OrderRequestResponse;

import java.util.List;

public interface OrderService {

    int makeOrder(String email, OrderRequestResponse orderRequestResponse);

    List<OrderRequestResponse> selectOrderById(String email, Boolean isRecent);
}
