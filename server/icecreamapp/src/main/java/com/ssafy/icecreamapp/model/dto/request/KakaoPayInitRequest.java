package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayInitRequest {
    String partner_user_id;
    String item_name;
    int quantity;
    int total_amount;
}
