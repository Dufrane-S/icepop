package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayInitRequest {
    @Schema(example = "test1",description = "유저 이메일")
    String partner_user_id;
    @Schema(example = "뉴욕 치즈 케이크 외 1건", description = "품목명")
    String item_name;
    @Schema(example = "2", description = "수량")
    int quantity;
    @Schema(example = "7000")
    int total_amount;
}
