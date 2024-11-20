package com.ssafy.icecreamapp.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class IceSelectCon {
    String type;
    int rate;

    public IceSelectCon(String type, int rate) {
        this.type = type;
        this.rate = rate;
    }
}
