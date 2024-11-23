package com.ssafy.icecreamapp.service;

import com.ssafy.icecreamapp.exception.NoSuchElementsException;
import com.ssafy.icecreamapp.model.dao.*;
import com.ssafy.icecreamapp.model.dto.*;
import com.ssafy.icecreamapp.model.dto.request.OrderCon;
import com.ssafy.icecreamapp.model.dto.request.OrderDetailRequest;
import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import com.ssafy.icecreamapp.model.dto.respond.MemberInfo;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final IcecreamDao icecreamDao;
    private final MemberDao memberDao;
    private final FirebaseCloudMessageServiceWithData fcmService;
    private final NotificationDao notificationDao;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


    @Override
    @Transactional
    public int makeOrder(OrderRequest orderRequest) {
        Order order = new Order(orderRequest);
        log.info("makeOrder : {}", orderRequest);

//      이메일로 멤버 정보 획득
        Member member = memberDao.selectByEmail(orderRequest.getEmail());
        int memberId = member.getId();
        order.setMemberId(memberId);
        log.info("makeOrder : {}", member);

//      orderdetail 추출하여 합계 생성
        List<OrderDetailRequest> requestList = orderRequest.getDetails();
        List<OrderDetail> list = new ArrayList<>();
        for (OrderDetailRequest request : requestList) {
            list.add(new OrderDetail(request));
        }
        log.info("makeOrder : {}", list);

//      아이스크림 정보 불러와서 계산 일일히 조회
        /*for (OrderDetail detail : list) {
            Icecream icecream = icecreamDao.selectIcecreamById(detail.getProductId());
            int price = (int) (icecream.getPrice() * ((100.0f - icecream.getIsEvent()) / 100.0f));
            icecreamDao.updateIcecreamById(icecream.getId(), detail.getQuantity(), member.getAge(), member.getGender());
            sumPrice += detail.getQuantity() * price;
        }*/

        //      아이스크림 정보 불러와서 계산 한번에
        int sumPrice = 0;
        List<Integer> idList = new ArrayList<>();
        for (OrderDetail detail : list) {
            idList.add(detail.getProductId());
        }
        List<Icecream> icecreamList = icecreamDao.selectIcecreamsByIds(idList);
        for (OrderDetail detail : list) {
            for (Icecream icecream : icecreamList) {
                if (detail.getProductId() == icecream.getId()) {
                    int price = (int) ((icecream.getPrice() * ((100.0f - icecream.getIsEvent()) / 100.0f)) / 10 * 10); // 개별 가격 할인 원단위 절사
                    icecreamDao.updateIcecreamById(icecream.getId(), detail.getQuantity(), member.getAge(), member.getGender());
                    sumPrice += detail.getQuantity() * price;
                    break;
                }
            }
        }
        sumPrice = sumPrice / 10 * 10; // 원단위 절사

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
        log.info("makeOrder " + orderRequest);
        int result = memberDao.updateSum(orderRequest.getEmail(), sumPrice);

        try {
            fcmService.sendDataMessageTo("fExca8C9R-Cb_3g16LxDYL:APA91bE8Mi6KPtsxvnqzADGwnKTuc7_-xZcylK8MZjZBgQb-XlexU6nefM8tRoG36-IZcZbq1o_kro782RDhLQXYkcsOFN1g_KjV6wXpYbGFyC6E5uqFhtg",
                    "주문 접수", order.getId() + "번 주문이 접수되었습니다.");

            notificationDao.insertNotification(new Notification(memberId, order.getId(), 1));

            scheduledExecutorService.schedule(() -> {
                try {
                    fcmService.sendDataMessageTo("fExca8C9R-Cb_3g16LxDYL:APA91bE8Mi6KPtsxvnqzADGwnKTuc7_-xZcylK8MZjZBgQb-XlexU6nefM8tRoG36-IZcZbq1o_kro782RDhLQXYkcsOFN1g_KjV6wXpYbGFyC6E5uqFhtg",
                            "준비 완료", order.getId() + "번 주문이 준비 완료되었습니다.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                notificationDao.insertNotification(new Notification(memberId, order.getId(), 2));
            }, 10, TimeUnit.SECONDS);

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
                throw new NoSuchElementsException("없는 사용자의 id : " + memberId);
            }
        }
        return orderDao.selectWithResultmap2(orderCon, memberId);
    }
}
