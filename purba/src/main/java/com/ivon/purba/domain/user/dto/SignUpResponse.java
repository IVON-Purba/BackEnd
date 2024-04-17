package com.ivon.purba.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SignUpResponse {
    private String message;

    public SignUpResponse() {
        this.message = "회원가입을 성공했습니다!";
    }
}
