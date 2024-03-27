package com.ivon.purba.service;

import com.ivon.purba.dto.userController.SignUpRequest;
import com.ivon.purba.exception.InvalidPhoneNumberPatternException;
import com.ivon.purba.exception.UserAlreadyExistException;
import com.ivon.purba.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("동일한 사용자 정보로 회원가입")
    void signUpWithDuplicatePhoneNumber() {
        //given
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setPhoneNumber(generateRandomPhoneNumber());

        //when
        userService.signUp(signUpRequest);
        UserAlreadyExistException e = assertThrows(UserAlreadyExistException.class, () -> {
            userService.signUp(signUpRequest);
        });

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 유저입니다.");
    }

    @Test
    @DisplayName("유효하지 않은 전화번호로 회원가입")
    void signUpWithInvalidPhoneNumberPatter(){
        //given
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setPhoneNumber("My nave is Yongmin Kim");

        //when
        InvalidPhoneNumberPatternException e = assertThrows(InvalidPhoneNumberPatternException.class, () -> {
            userService.signUp(signUpRequest);
        });

        //then
        assertThat(e.getMessage()).isEqualTo("폰 번호 형식이 올바르지 않습니다.");
    }


    private String generateRandomPhoneNumber() {
        int middle = ThreadLocalRandom.current().nextInt(1000, 10000);
        int last = ThreadLocalRandom.current().nextInt(1000, 10000);
        return String.format("010-%04d-%04d", middle, last);
    }
}