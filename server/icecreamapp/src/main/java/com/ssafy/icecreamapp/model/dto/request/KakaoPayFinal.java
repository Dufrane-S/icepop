package com.ssafy.icecreamapp.model.dto.request;

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
}
