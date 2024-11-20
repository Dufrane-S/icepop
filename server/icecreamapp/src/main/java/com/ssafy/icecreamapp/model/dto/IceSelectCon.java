package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IceSelectCon {
    String type;
    int rate;
    boolean isRecommend;

    public IceSelectCon(String type, int rate, boolean isRecommend) {
        this.type = type;
        this.rate = rate;
        this.isRecommend = isRecommend;
    }
}
