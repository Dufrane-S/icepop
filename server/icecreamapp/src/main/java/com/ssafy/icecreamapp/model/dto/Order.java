package com.ssafy.icecreamapp.model.dto;

import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order {
    int id;
    int memberId;
    long date;
    int spoon;
    int dryice;
    int isForHere;
    int priceSum;
    int discountSum;
    int resultSum;

    public Order(OrderRequest orderRequest) {
        this.date = System.currentTimeMillis();
        this.spoon = orderRequest.getSpoon();
        this.dryice = orderRequest.getDryice();
        this.isForHere = orderRequest.getIsForHere();
        this.priceSum = orderRequest.getPriceSum();
        this.discountSum = orderRequest.getDiscountSum();
        this.resultSum = orderRequest.getResultSum();
    }
}
