package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InitMember {
    @Schema(description = "닉네임(이름)", example = "qwer")
    String name;
    @Schema(description = "이메일", example = "qwer")
    String email;
    @Schema(description = "암호", example = "qwer")
    String password;
    @Schema(description = "남자 1, 여자 2", example = "1")
    int gender;
    @Schema(description = "연령", example = "20")
    int age;
}
