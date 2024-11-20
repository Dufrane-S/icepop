package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    int id;
    String name;
    String email;
    String password;
    int purchaseSum;
    int gender;
    int age;

}
