package com.ivon.purba.domain.user.dto;

import lombok.Getter;

@Getter
public class SignOutResponse {
    private String message;

    public SignOutResponse() {
        this.message = "로그아웃을 성공했습니다.";
    }
}
