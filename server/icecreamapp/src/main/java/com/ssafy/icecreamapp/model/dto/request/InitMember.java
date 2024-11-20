package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitMember {
    String name;
    String email;
    String password;
    int gender;
    int age;
}
