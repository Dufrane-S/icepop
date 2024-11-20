package com.ssafy.icecreamapp.model.dao;

import com.ssafy.icecreamapp.model.dto.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailDao {
    int insertDetail(OrderDetail detail);

    List<OrderDetail> selectOrderDetailsByOrderId(int orderId);

}
