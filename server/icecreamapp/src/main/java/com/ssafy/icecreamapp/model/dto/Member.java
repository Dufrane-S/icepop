package com.ssafy.icecreamapp.model.dto;

import com.ssafy.icecreamapp.model.dto.request.InitMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    int id;
    String name;
    String email;
    String password;
    int purchaseSum;
    int gender;
    int age;

    public Member(InitMember initMember) {
        this.name = initMember.getName();
        this.email = initMember.getEmail();
        this.password = initMember.getPassword();
        this.age = initMember.getAge();
        this.gender = initMember.getGender();
    }
}
