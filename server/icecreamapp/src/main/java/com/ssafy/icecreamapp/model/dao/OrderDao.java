package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Order;
import com.ssafy.icecreamapp.model.dto.request.OrderCon;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {
    int insertOrder(Order order);

    List<Order> selectOrderByEmail(int memberId, Boolean isRecent);

    List<OrderInfo> selectWithResultmap(@Param("orderCon") OrderCon orderCon, @Param("memberId") int memberId);

    List<OrderInfo> selectWithResutmap2(@Param("memberId") int memberId);

}