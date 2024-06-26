package com.ivon.purba.domain.sms.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SmsServiceSendRequest {
    @NotEmpty(message = "Phone number cannot be empty.")
    private String toPhoneNumber;
}
