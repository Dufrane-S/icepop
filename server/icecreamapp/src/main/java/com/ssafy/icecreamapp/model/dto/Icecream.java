package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Icecream {
    int id;
    String name;
    int price;
    int isEvent;
    String type;
    int kcal;
    String img;
    String content;
    int count;
}
