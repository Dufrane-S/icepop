package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.model.dto.request.OrderCon;
import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;

import java.util.List;

public interface OrderService {

    int makeOrder(OrderRequest orderRequest);

    List<OrderInfo> selectOrdersByEmail(String email, Boolean isRecent);

    List<OrderInfo> selectOrdersWithCon(OrderCon orderCon);
}