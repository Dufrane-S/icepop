package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewCon {
    @Schema(example = "test1", description = "이메일")
    String email;
    @Schema(example = "0", description = "주문번호")
    int orderId;
    @Schema(example = "false", description = "최근 조회 여부")
    boolean recent;
}
