package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.exception.MyNoSuchElementException;
import com.ssafy.icecreamapp.model.dao.*;
import com.ssafy.icecreamapp.model.dto.*;
import com.ssafy.icecreamapp.model.dto.request.OrderCon;
import com.ssafy.icecreamapp.model.dto.request.OrderDetailRequest;
import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final MemberDao memberDao;
    private final FirebaseCloudMessageServiceWithData fcmService;
    private final NotificationDao notificationDao;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    private final IcecreamDao icecreamDao;

    @Override
    @Transactional
    public int makeOrder(OrderRequest orderRequest) {
        Order order = new Order(orderRequest);

//      이메일로 멤버 정보 획득
        Member member = memberDao.selectByEmail(orderRequest.getEmail());
        int memberId = member.getId();
        order.setMemberId(memberId);

//      orderdetail 추출하여 합계 생성
        List<OrderDetailRequest> requestList = orderRequest.getDetails();
        List<OrderDetail> list = new ArrayList<>();
        for (OrderDetailRequest request : requestList) {
            list.add(new OrderDetail(request));
        }

//      아이스크림 정보 불러와서 계산 일일히 조회
        /*for (OrderDetail detail : list) {
            Icecream icecream = icecreamDao.selectIcecreamById(detail.getProductId());
            int price = (int) (icecream.getPrice() * ((100.0f - icecream.getIsEvent()) / 100.0f));
            icecreamDao.updateIcecreamById(icecream.getId(), detail.getQuantity(), member.getAge(), member.getGender());
            sumPrice += detail.getQuantity() * price;
        }*/


//      order부터 insert
        orderDao.insertOrder(order);

//      detail insert
        for (OrderDetail detail : list) {
            detail.setOrderId(order.getId());
            orderDetailDao.insertDetail(detail);
            //아이스크림 판매량 업데이트
            icecreamDao.updateIcecreamById(detail.getProductId(), detail.getQuantity(), member.getAge(), member.getGender());
        }
        log.info("order : {}", order);
        log.info("orderDetails : {}", list);
        log.info("orderReqeust {}", orderRequest);

        //member 주문 금액 update
        int result = memberDao.updateSum(orderRequest.getEmail(), orderRequest.getDiscountSum());

        if (member.getNotificationToken() == null) {
            throw new MyNoSuchElementException("토큰", member.getEmail());
        }
        try {
            fcmService.sendDataMessageTo(member.getNotificationToken(),
                    "주문 접수", order.getId() + "번 주문이 접수되었습니다.");

            notificationDao.insertNotification(new Notification(memberId, order.getId(), 1));

            scheduledExecutorService.schedule(() -> {
                try {
                    fcmService.sendDataMessageTo(member.getNotificationToken(),
                            "준비 완료", order.getId() + "번 주문이 준비 완료되었습니다.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                notificationDao.insertNotification(new Notification(memberId, order.getId(), 2));
            }, 10 + (int) (Math.random() * 6), TimeUnit.SECONDS);

        } catch (IOException e) {
            log.error("알림 발송 중 오류");
            e.printStackTrace();
            throw new RuntimeException();
        }


        return result;
    }

//    주문마다 디테일을 가져오는 방법, 주문 리스트 조회 1번, 주문만큼 디테일 조회 n번
    /*@Override
    public List<OrderInfo> selectOrdersByEmail(String email, boolean isRecent) {
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
    }*/

//  db 내부에서 join하는 방법 resultMap을 통해 OrderInfo에 넣기 가능 db 접근 1회
//  하지만 최근 n건 조회가 불가 -> join할 시 column이 order 갯수가 아닌 orderdetail 갯수로 나오기 때문
//  order 3개가 각각 2개의 detail 보유시 limit 5를하면 orderdetail이 5개만 나옴
    /*@Override
    public List<OrderInfo> selectOrdersWithCon(OrderCon orderCon) {
        int memberId = 0;
        if (!orderCon.getEmail().isEmpty()) {
            Member member = memberDao.selectByEmail(orderCon.getEmail());
            if (member != null) {
                memberId = member.getId();
            } else {
                throw new NoSuchElementsException("없는 사용자의 id : " + memberId);
            }
        }
        return orderDao.selectWithResultmap(orderCon, memberId);
    }*/

    //
    @Override
    public List<OrderInfo> selectOrdersWithCon2(OrderCon orderCon) {
        int memberId = 0;
        if (!orderCon.getEmail().equals("")) {
            Member member = memberDao.selectByEmail(orderCon.getEmail());
            if (member != null) {
                memberId = member.getId();
            } else {
                throw new MyNoSuchElementException("이메일", orderCon.getEmail());
            }
        }
        List<OrderInfo> orderInfos = orderDao.selectWithResultmap2(orderCon, memberId);
        // 제품 종류 몇가지인지 추가
        for (OrderInfo orderInfo : orderInfos) {
            orderInfo.setCategoryCount(orderInfo.getDetails().size());
            for (OrderDetail detail : orderInfo.getDetails()) {
                float discountRate = (float) (100 - detail.getIsEvent()) / 100;
                int resultPrice = (int) (detail.getPrice() * discountRate) / 10 * 10;
                detail.setDiscountedPrice(resultPrice);
            }
        }
        if (orderCon.getOrderId() != 0 && orderInfos.isEmpty()) {
            throw new MyNoSuchElementException("주문 번호", Integer.toString(orderCon.getOrderId()));
        }
        return orderInfos;
    }
}
