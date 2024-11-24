package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailRequest {
    @Schema(example = "1", description = "제품 번호")
    int productId;
    @Schema(example = "1", description = "제품 수량")
    int quantity;
}
