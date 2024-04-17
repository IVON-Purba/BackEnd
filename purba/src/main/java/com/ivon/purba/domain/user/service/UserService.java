package com.ivon.purba.domain.user.service;

import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.dto.SignUpRequest;

public interface UserService {
    // 회원가입
    void signUp(SignUpRequest request);

    Long signIn(String phoneNumber);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);
}
