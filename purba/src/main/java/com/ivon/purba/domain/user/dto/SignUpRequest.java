package com.ivon.purba.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
@Getter
public class SignUpRequest {
    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @NotEmpty(message = "Phone number cannot be empty.")
    private String phoneNumber;
}
