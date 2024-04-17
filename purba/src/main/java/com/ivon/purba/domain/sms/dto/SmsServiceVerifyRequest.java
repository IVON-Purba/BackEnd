package com.ivon.purba.domain.sms.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SmsServiceVerifyRequest {
    @NotEmpty(message = "Phone number cannot be empty.")
    private String toPhoneNumber;
    @NotEmpty(message = "Validation code cannot be empty.")
    private String validateCode;
}

