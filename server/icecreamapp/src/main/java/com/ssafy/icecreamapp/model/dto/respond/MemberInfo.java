package com.ssafy.icecreamapp.model.dto.respond;

import com.ssafy.icecreamapp.model.dto.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberInfo {
    String name;
    String email;
    String level;
    int gender;
    int age;
    int nextLvRemain;
    float discountRate;
    String img;

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
            this.level = "새싹";
        } else if (lev == 2) {//150000이하
            this.level = "꽃";
        } else if (lev == 3) {//200000이하
            this.level = "열매";
        } else if (lev > 3) {//250000이하
            this.level = "나무";
        }
        this.nextLvRemain = 50000 - (purchaseSum % 50000);
        if (this.level.equals("나무")) nextLvRemain = 0;

        if (this.level.equals("씨앗")) {
            this.discountRate = 0.99f;
            this.img = "seed";
        } else if (this.level.equals("새싹")) {
            this.discountRate = 0.97f;
            this.img = "plant";
        } else if (this.level.equals("꽃")) {
            this.discountRate = 0.95f;
            this.img = "flower";
        } else if (this.level.equals("열매")) {
            this.discountRate = 0.93f;
            this.img = "berry";
        } else {
            this.discountRate = 0.91f;
            this.img = "tree";
        }

    }

}
