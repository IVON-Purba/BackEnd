package com.ivon.purba.domain.sms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsServiceVerifyRequest {
    private String phoneNumber;
    private String validateCode;
}

