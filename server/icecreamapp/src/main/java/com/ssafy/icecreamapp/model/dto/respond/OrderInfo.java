package com.ssafy.icecreamapp.model.dto.respond;

import com.ssafy.icecreamapp.model.dto.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderInfo {
    private int id;
    private long date;
    private int spoon;
    private int dryice;
    private int priceSum;
    private String email;
    private List<OrderDetail> details;
}
