package com.ivon.purba.domain.sms.dto;

import com.ivon.purba.domain.security.dto.JwtToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsServiceVerifyResponse {
    JwtToken token;
    private String message;

    public SmsServiceVerifyResponse(JwtToken token) {
        this.token = token;
        this.message = "요청이 확인되었습니다.";
    }
}
