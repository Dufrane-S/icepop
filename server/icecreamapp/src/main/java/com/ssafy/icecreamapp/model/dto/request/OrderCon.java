package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderCon {
    @Schema(example = "test1", description = "이메일")
    String email;
    @Schema(example = "0", description = "제품 번호")
    int orderId;
    @Schema(example = "false", description = "최근 목록 조회 여부")
    boolean isRecent;

    public OrderCon(String email, int orderId, boolean isRecent) {
        this.email = email;
        this.orderId = orderId;
        this.isRecent = isRecent;
    }
}

