package com.ssafy.icecreamapp.model.dto;

import com.ssafy.icecreamapp.model.dto.request.KakaoPayInitRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPay {
    String cid;
    String partner_order_id;
    String partner_user_id;
    String item_name;
    int quantity;
    int total_amount;
    int tax_free_amount;
    String approval_url;
    String cancel_url;
    String fail_url;
    String tid;
    String pg_token;

    public KakaoPay(KakaoPayInitRequest kakaoPayInitRequest) {
        this.cid = "TC0ONETIME";
        this.item_name=kakaoPayInitRequest.getItem_name();
        this.partner_user_id = kakaoPayInitRequest.getPartner_user_id();
        this.quantity = kakaoPayInitRequest.getQuantity();
        this.total_amount = kakaoPayInitRequest.getTotal_amount();
        this.tax_free_amount = 0;
        this.approval_url = "http://192.168.219.177:8080/kakao/approved";
        this.fail_url = "http://192.168.219.177:8080/failed";
        this.cancel_url = "http://192.168.219.177:8080/canceled";
    }
}
