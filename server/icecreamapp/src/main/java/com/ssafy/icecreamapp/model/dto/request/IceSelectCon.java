package com.ssafy.icecreamapp.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IceSelectCon {
    String type;
    int rate;
    int age;
    int gender;

    public IceSelectCon(String type, int rate, int age, int gender) {
        this.type = type;
        this.rate = rate;
        this.age = age;
        this.gender = gender;
    }
}
