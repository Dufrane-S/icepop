package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;

import java.util.List;

public interface OrderService {

    int makeOrder(OrderRequest orderRequest);

    List<OrderInfo> selectOrderById(String email, Boolean isRecent);
}
