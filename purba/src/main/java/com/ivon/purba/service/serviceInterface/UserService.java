package com.ivon.purba.service.serviceInterface;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.userController.SignUpRequest;

public interface UserService {
    // 회원가입
    void signUp(SignUpRequest request);

    Long signIn(String phoneNumber);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);
}
