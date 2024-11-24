package com.ssafy.icecreamapp.model.dto.request;

import com.ssafy.icecreamapp.model.dto.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequest {
    @Schema(example = "test1", description = "이메일")
    private String email;
    @Schema(example = "3", description = "숟가락 갯수")
    private int spoon;
    @Schema(example = "60", description = "드라이아이스 시간")
    private int dryice;
    @Schema(example = "1", description = "매장/포장 여부, 1이면 매장, 2이면 포장")
    private int isForHere;
    @Schema(example = "3500",description = "할인 받기 전 소비자가의 최종합 ex) 아이스크림류 2개 = 3500 + 3500")
    private int priceSum;
    @Schema(example = "210")
    private int discountSum;
    @Schema(example = "3290")
    private int resultSum;
    private List<OrderDetailRequest> details;
}
