package com.ivon.purba.domain.user.service.interfaces;

import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.dto.SignUpRequest;

public interface UserService {
    // 회원가입
    void signUp(SignUpRequest request);

    Long signIn(String phoneNumber);

    //로그아웃
    void signOut(String token);

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);
}
