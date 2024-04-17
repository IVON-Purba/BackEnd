package com.ivon.purba.domain.user.controller;

import com.ivon.purba.domain.user.dto.*;
import com.ivon.purba.domain.user.service.UserAuthenticationServiceImpl;
import com.ivon.purba.domain.user.service.UserRegistrationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserAuthenticationServiceImpl userAuthService;
    private final UserRegistrationServiceImpl userRegService;

    @GetMapping(value = "/user/signIn")
    public ResponseEntity<Object> userSignIn(@Valid @RequestBody SignInRequest request) {
        Long userId = userAuthService.signIn(request.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.OK).body(new SignInResponse(userId));
    }

    @PostMapping(value = "/user/signUp")
    public ResponseEntity<?> userSignUp(@Valid @RequestBody SignUpRequest request) {
        userRegService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse());
    }

    @PostMapping( "/user/signOut")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        userAuthService.signOut(token);
        return ResponseEntity.status(HttpStatus.OK).body(new SignOutResponse());
    }
}