package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    int insertOrder(Order order);

    List<Order> selectOrderByEmail(int memberId, Boolean isRecent);
}
