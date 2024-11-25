package com.ssafy.icecreamapp.model.dto.respond;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayResult {
    String tid;
    boolean tms_result;
    String created_at;
    String next_redirect_pc_url;
    String next_redirect_mobile_url;
    String next_redirect_app_url;
    String android_app_scheme;
    String ios_app_scheme;

    public KakaoPayResult(JsonNode response) {
        this.tid = response.path("tid").asText();
        this.tms_result = response.path("tms_result").asBoolean();
        this.created_at = response.path("created_at").asText();
        this.next_redirect_pc_url = response.path("next_redirect_pc_url").asText();
        this.next_redirect_mobile_url = response.path("next_redirect_mobile_url").asText();
        this.next_redirect_app_url = response.path("next_redirect_app_url").asText();
        this.android_app_scheme = response.path("android_app_scheme").asText();
        this.ios_app_scheme = response.path("ios_app_scheme").asText();
    }
}
