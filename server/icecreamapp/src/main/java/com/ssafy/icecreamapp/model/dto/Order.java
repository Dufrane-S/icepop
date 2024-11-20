package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    int id;
    int memberId;
    long date;
    int spoon;
    int dryice;
    int priceSum;
}
