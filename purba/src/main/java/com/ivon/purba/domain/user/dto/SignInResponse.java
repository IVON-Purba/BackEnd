package com.ivon.purba.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponse {
    private String message;
    Long userId;

    public SignInResponse(Long  userId) {
        this.userId = userId;
        this.message = "로그인을 성공했습니다.";
    }
}
