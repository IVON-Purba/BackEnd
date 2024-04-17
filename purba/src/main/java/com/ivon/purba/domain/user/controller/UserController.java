package com.ivon.purba.domain.user.controller;

import com.ivon.purba.domain.user.dto.*;
import com.ivon.purba.domain.user.service.UserServiceImpl;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    //로그인
    @GetMapping(value = "/user/signIn")
    public ResponseEntity<Object> userSignIn(@RequestBody SignInRequest request) {
        Long userId = userService.signIn(request.getPhoneNumber());

        return ResponseEntity.status(HttpStatus.OK).body(new SignInResponse(userId));
    }

    //회원가입
    @PostMapping(value = "/user/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse());
    }

    //로그아웃
    @PostMapping( "/user/signOut")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        userService.signOut(token);

        return ResponseEntity.status(HttpStatus.OK).body(new SignOutResponse());
    }
}
