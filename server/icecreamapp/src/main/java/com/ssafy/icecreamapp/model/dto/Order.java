package com.ssafy.icecreamapp.model.dto;

import com.ssafy.icecreamapp.model.dto.request.OrderRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    int id;
    int memberId;
    long date;
    int spoon;
    int dryice;
    int priceSum;

    public Order(OrderRequest orderRequest) {
        this.date=System.currentTimeMillis();
        this.spoon=orderRequest.getSpoon();
        this.dryice=orderRequest.getDryice();
    }
}
