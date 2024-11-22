package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InitReview {
    @Schema(example = "test1", description = "이메일")
    String email;
    @Schema(example = "1", description = "주문번호")
    int orderId;
    @Schema(example = "4.5", description = "평점")
    float rate;
    @Schema(example = "맛없음", description = "후기 내용")
    String content;
}
