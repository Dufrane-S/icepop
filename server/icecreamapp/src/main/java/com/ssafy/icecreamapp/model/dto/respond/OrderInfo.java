package com.ssafy.icecreamapp.model.dto.respond;

import com.ssafy.icecreamapp.model.dto.OrderDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderInfo {
    private int id;
    private long date;
    private int spoon;
    private int dryice;
    private int priceSum;
    private String email;
    private int purchaseSum;
    private List<OrderDetail> details;
}
