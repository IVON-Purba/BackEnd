package com.ivon.purba.service.user;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.userController.SignInRequest;
import com.ivon.purba.dto.userController.SignUpRequest;
import com.ivon.purba.exception.exceptions.InvalidPhoneNumberPatternException;
import com.ivon.purba.exception.exceptions.UserAlreadyExistException;
import com.ivon.purba.exception.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Rollback
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("성공적인 회원가입")
    void userSignUpSuccess() {
        //given
        String phoneNumber = generateRandomPhoneNumber();
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setPhoneNumber(phoneNumber);

        //when
        userService.signUp(signUpRequest);

        //then
        User user = userService.getUserByPhoneNumber(phoneNumber);
        assertThat(user).isNotNull();
    }

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

    @Test
    @DisplayName("성공적인 로그인")
    void userSignInSuccess() {
        //given
        String phoneNumber = generateRandomPhoneNumber();
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setPhoneNumber(phoneNumber);
        userService.signUp(signUpRequest);

        //when
        Long userId = userService.signIn(phoneNumber);
        User user = userService.getUserById(userId);

        //then
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(user.getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 로그인 시도")
    void userSignInWithNonExistingUser() {
        //given
        String phoneNumber = generateRandomPhoneNumber();
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setPhoneNumber(phoneNumber);
        userService.signUp(signUpRequest);

        //when
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> {
            userService.signIn(generateRandomPhoneNumber());
        });

        //then
        assertThat(e.getMessage()).isEqualTo("해당 회원이 존재하지 않습니다.");
    }

    private String generateRandomPhoneNumber() {
        int middle = ThreadLocalRandom.current().nextInt(1000, 10000);
        int last = ThreadLocalRandom.current().nextInt(1000, 10000);
        return String.format("010-%04d-%04d", middle, last);
    }
}
