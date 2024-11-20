package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Order;
import com.ssafy.icecreamapp.model.dto.OrderDetail;
import com.ssafy.icecreamapp.model.dto.OrderRequestResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    int insertOrder(OrderRequestResponse orderRequestResponse);

    List<Order> selectOrderByEmail(int memberId, Boolean isRecent);
}
