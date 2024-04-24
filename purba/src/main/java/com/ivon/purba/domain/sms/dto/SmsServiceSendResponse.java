package com.ivon.purba.domain.sms.dto;

import lombok.Getter;

@Getter
public class SmsServiceSendResponse {
    private String message;

    public SmsServiceSendResponse() {
        this.message = "인증번호 발송이 완료되었습니다.";
    }
}
