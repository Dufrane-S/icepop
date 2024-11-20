package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestResponse {
    private int id;
    private int memberId;
    private long date;
    private int spoon;
    private int dryice;
    private int priceSum;
    private String email;
    private List<OrderDetail> details;
}
