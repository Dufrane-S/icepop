package com.ssafy.icecreamapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    @Schema(description = "이메일", example = "test1")
    String email;
    @Schema(description = "토큰", example = "android_token")
    String token;

    public Token(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
