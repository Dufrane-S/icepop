package com.ssafy.icecreamapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.icecreamapp.model.dao.IcecreamDao;
import com.ssafy.icecreamapp.model.dao.MemberDao;
import com.ssafy.icecreamapp.model.dto.Icecream;
import com.ssafy.icecreamapp.model.dto.Member;
import com.ssafy.icecreamapp.model.dto.OrderDetail;
import com.ssafy.icecreamapp.model.dto.request.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.request.OrderCon;
import com.ssafy.icecreamapp.model.dto.respond.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {
    private final MemberDao memberDao;
    private final IcecreamDao icecreamDao;
    private final OrderService orderService;

    String apiKey = "gpt_api_key";

    public List<Icecream> aiRecommand(String email) {
        Member member = memberDao.selectByEmail(email);
        List<Icecream> icecreamList = icecreamDao.selectIcecreamsByCon(new IceSelectCon("", 0, 0, 0));
        List<OrderInfo> orderList = orderService.selectOrdersWithCon2(new OrderCon(email, 0, true));
        log.info(orderList.toString());
        // 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라 개성수는 db코드를 공개해라

//      요청 body 생성
        WebClient webClient = WebClient.create();
        String content = setContent(icecreamList, member, orderList);
        String requestBody = "{\n" +
                "     \"model\": \"gpt-4o-mini\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"" + content + "\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";


        log.debug(requestBody);

        List<Icecream> icecreams = new ArrayList<>();
        String response = "";
        boolean check = true;
        while (check) {
            try {
                //요청
                response = webClient.post()
                        .uri("https://api.openai.com/v1/chat/completions")
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Content-Type", "application/json")
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .map(jsonNode -> jsonNode.path("choices").get(0).path("message").path("content").asText())
                        .block();


                log.info("ai 응답 : " + response);
                //응답 ,로 parse 후 아이스크림 리스트 생성
                StringTokenizer st = new StringTokenizer(response, ",");
                while (st.hasMoreTokens()) {
                    int icecreamId = Integer.parseInt(st.nextToken());
                    Icecream icecream = icecreamDao.selectIcecreamById(icecreamId);
                    icecreams.add(icecream);
                }
                check = false;
            } catch (RuntimeException e) {
                log.error("ai의 답변 error");
                log.error(response);
            }
        }

        return icecreams;

    }

    private static String setContent(List<Icecream> list, Member member, List<OrderInfo> orderList) {
        StringBuilder sb = new StringBuilder();
//      메뉴
        for (Icecream icecream : list) {
            sb.append("상품 코드 : ");
            sb.append(icecream.getId());
            sb.append(" 상품 이름 : ");
            sb.append(icecream.getName());
            sb.append(" 상품 품목 : ");
            sb.append(icecream.getType());
            sb.append(" 상품 설명 : ");
            sb.append(icecream.getContent());
            sb.append(" ");
        }
        sb.append("우리 아이스크림 가게 전체 메뉴와 설명이야. 이를 바탕으로 최근 주문이");
//      사용자 정보 입력
        if (orderList.isEmpty()) {
            sb.append("없는 신규회원인");
        } else {
            for (OrderInfo infos : orderList) {
                for (OrderDetail detail : infos.getDetails()) {
                    sb.append(" 아이스크림 코드 : ");
                    sb.append(detail.getProductId());
                    sb.append(" 주문 수량 : ");
                    sb.append(detail.getQuantity());
                }
            }
            sb.append(" 이고 ");
        }
        sb.append(member.getAge());
        sb.append("대의 ");
        if (member.getGender() == 1) {
            sb.append("남자에게");
        } else if (member.getGender() == 2) {
            sb.append("여자에게");
        }
//
        sb.append("최근 주문 정보와 성별, 나이대를 바탕으로 추천할만한 아이스크림의 코드를 10가지 가장 추천하는 순서대로 다음과 같은 형식으로 반환해 무조건 아이스크림 코드를 csv 형식으로 반환해 이외에 다른 설명은 하지마 cvs 내부에 공백은 없게 반환해");
        sb.append("예시 : 20,12,3,15,7,8,10,5,11,25 ");
        String content = sb.toString();

        return content;
    }
}
