package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.model.dao.OrderDetailDao;
import com.ssafy.icecreamapp.model.dto.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailDao orderDetailDao;

    @Override
    public int insertOrderDetail(OrderDetail orderDetail) {
        return orderDetailDao.insertDetail(orderDetail);
    }
}
