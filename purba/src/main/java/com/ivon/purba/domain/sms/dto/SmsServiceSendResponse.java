package com.ivon.purba.domain.sms.dto;

import lombok.Getter;
import lombok.Setter;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@Getter
@Setter
public class SmsServiceSendResponse {
    private String message;

    public SmsServiceSendResponse() {
        this.message = "인증번호 발송이 완료되었습니다.";
    }
}
