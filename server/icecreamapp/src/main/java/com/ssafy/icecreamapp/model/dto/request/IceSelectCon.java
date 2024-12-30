package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IceSelectCon {
    @Schema(example = "아이스크림" ,description = "빈칸 ('')일시 모든 종류, 아이스크림, 케이크, 레디팩")
    String type;
    @Schema(example = "0", description = "할인율이 입력값 % 이상인 제품 리스트 반환")
    int rate;
    @Schema(example = "0", description = "추천을 위한 나이, gender와 같이 입력")
    int age;
    @Schema(example = "0", description = "추천을 위한 성별, age와 같이 입력")
    int gender;

    public IceSelectCon(String type, int rate, int age, int gender) {
        this.type = type;
        this.rate = rate;
        this.age = age;
        this.gender = gender;
    }
}
