package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InitMember {
    String name;
    String email;
    String password;
    int gender;
    int age;
}
