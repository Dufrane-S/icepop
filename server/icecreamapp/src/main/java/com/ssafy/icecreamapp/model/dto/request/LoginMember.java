package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginMember {
    @Schema(description = "이메일 주소", example = "test1")
    String email;
    @Schema(description = "암호", example = "test1")
    String password;
}
