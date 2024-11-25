package com.ssafy.icecreamapp.model.dto.request;

import com.ssafy.icecreamapp.model.dto.KakaoPay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayFinal {
    String cid;
    String tid;
    String partner_order_id;
    String partner_user_id;
    String pg_token;

    public KakaoPayFinal(KakaoPay kakaoPay) {
        this.cid=kakaoPay.getCid();
        this.tid=kakaoPay.getTid();
        this.partner_order_id=kakaoPay.getPartner_order_id();
        this.partner_user_id=kakaoPay.getPartner_user_id();
    }
}
