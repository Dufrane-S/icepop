package com.ssafy.icecreamapp.model.service;

import com.ssafy.icecreamapp.model.dao.IcecreamDao;
import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dao.OrderDao;
import com.ssafy.icecreamapp.model.dao.OrderDetailDao;
import com.ssafy.icecreamapp.model.dto.*;
import com.ssafy.icecreamapp.model.dto.request.OrderDetailRequest;
import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;
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
    public int makeOrder(OrderRequest orderRequest) {
        Order order = new Order(orderRequest);

//      이메일로 멤버 id 정보 획득
        Member member = memberDao.selectByEmail(orderRequest.getEmail());
        int memberId = member.getId();
        order.setMemberId(memberId);

//      orderdetail 추출하여 합계 생성
        List<OrderDetailRequest>requestList = orderRequest.getDetails();
        List<OrderDetail> list = new ArrayList<>();
        for(OrderDetailRequest request : requestList){
            list.add(new OrderDetail(request));
        }

        int sumPrice = 0;
//      아이스크림 정보 불러와서 계싼
        for (OrderDetail detail : list) {
            Icecream icecream = icecreamDao.selectIcecreamById(detail.getProductId());
            int price = (int) (icecream.getPrice() * ((100.0f - icecream.getIsEvent()) / 100.0f));
            icecreamDao.updateIcecreamById(icecream.getId(), detail.getQuantity());
            sumPrice += detail.getQuantity() * price;
        }
//      멤버 정보로 할인율 계산
        float discountRate = new MemberInfo(member).getDiscountRate();
        sumPrice = (int) (sumPrice * discountRate);

//      order부터 insert
        order.setPriceSum(sumPrice);
        orderDao.insertOrder(order);
//      detail insert
        for (OrderDetail detail : list) {
            detail.setOrderId(order.getId());
            orderDetailDao.insertDetail(detail);
        }

        int result = memberDao.updateSum(orderRequest.getEmail(), sumPrice);
        return result;
    }

    @Override
    public List<OrderInfo> selectOrderById(String email, Boolean isRecent) {
        Member member = memberDao.selectByEmail(email);
        List<Order> orderList = orderDao.selectOrderByEmail(member.getId(), isRecent);

        List<OrderInfo> result = new ArrayList<>();
        for (Order order : orderList) {
            log.debug(String.valueOf(order.getPriceSum()));
            OrderInfo dto = new OrderInfo();
            dto.setId(order.getId());
            dto.setDate(order.getDate());
            dto.setDryice(order.getDryice());
            dto.setSpoon(order.getSpoon());
            dto.setEmail(email);
            dto.setDetails(orderDetailDao.selectOrderDetailsByOrderId(order.getId()));
            dto.setPriceSum(order.getPriceSum());
            result.add(dto);
        }
        return result;
    }
}
