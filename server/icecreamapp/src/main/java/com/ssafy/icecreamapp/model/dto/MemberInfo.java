package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfo {
    String name;
    String email;
    String level;
    int gender;
    int age;
    int nextLvRemain;

    public MemberInfo(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.age = member.getAge();
        int purchaseSum = member.getPurchaseSum();
        int lev = purchaseSum / 50000;
        if (lev == 0) {//50000 이하
            this.level = "씨앗";
        } else if (lev == 1) {//100000이하
            this.level = "꽃";
        } else if (lev == 2) {//150000이하
            this.level = "열매";
        } else if (lev == 3) {//200000이하
            this.level = "커피콩";
        } else if (lev > 3) {//250000이하
            this.level = "나무";
        }
        this.nextLvRemain = 50000 - (purchaseSum % 50000);
        if (this.level.equals("나무")) nextLvRemain = 0;
    }
}
