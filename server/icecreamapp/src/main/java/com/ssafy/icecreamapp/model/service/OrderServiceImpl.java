package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dao.IcecreamDao;
import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dao.OrderDao;
import com.ssafy.icecreamapp.model.dao.OrderDetailDao;
import com.ssafy.icecreamapp.model.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final IcecreamDao icecreamDao;
    private final MemberDao memberDao;

    @Override
    @Transactional
    public int makeOrder(String email, OrderRequestResponse orderRequestResponse) {
        orderRequestResponse.setDate(System.currentTimeMillis());
        int memberId = memberDao.selectByEmail(email).getId();
        orderRequestResponse.setMemberId(memberId);
        List<OrderDetail> list = orderRequestResponse.getDetails();
        int sumPrice = 0;
        for (OrderDetail detail : list) {
            Icecream icecream = icecreamDao.selectIcecreamById(detail.getProductId());
            int price = (int) (icecream.getPrice() * ((100.0f - icecream.getIsEvent()) / 100.0f));
            icecreamDao.updateIcecreamById(icecream.getId(), detail.getQuantity());
            sumPrice += detail.getQuantity() * price;
        }
        orderRequestResponse.setPriceSum(sumPrice);
        orderDao.insertOrder(orderRequestResponse);
        for (OrderDetail detail : list) {
            detail.setOrderId(orderRequestResponse.getId());
            orderDetailDao.insertDetail(detail);
        }

        int result = memberDao.updateSum(email, sumPrice);
        return result;
    }

    @Override
    public List<OrderRequestResponse> selectOrderById(String email, Boolean isRecent) {
        Member member = memberDao.selectByEmail(email);
        List<Order> orderList = orderDao.selectOrderByEmail(member.getId(), isRecent);
        List<OrderRequestResponse> result = new ArrayList<>();
        for (Order order : orderList) {
            log.debug(String.valueOf(order.getPriceSum()));
            OrderRequestResponse dto = new OrderRequestResponse();
            dto.setId(order.getId());
            dto.setDate(order.getDate());
            dto.setDryice(order.getDryice());
            dto.setSpoon(order.getSpoon());
            dto.setMemberId(order.getMemberId());
            dto.setDetails(orderDetailDao.selectOrderDetailsByOrderId(order.getId()));
            dto.setPriceSum(order.getPriceSum());
            result.add(dto);
        }
        return result;
    }
}
