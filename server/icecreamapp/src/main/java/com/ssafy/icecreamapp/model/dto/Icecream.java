package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
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
