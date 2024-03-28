package com.ivon.purba.web;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.userController.*;
import com.ivon.purba.security.JwtTokenUtil;
import com.ivon.purba.service.UserServiceImpl;
import lombok.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    //로그인
    @GetMapping(value = "/user/signIn")
    public ResponseEntity<Object> userSignIn(@RequestBody SignInRequest request) {
        Long userId = userService.signIn(request.getPhoneNumber());

        SignInResponse response = new SignInResponse(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //회원가입
    @PostMapping(value = "/user/signUp")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);

        SingUpResponse response = new SingUpResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //로그아웃
    @PostMapping( "/user/signOut")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        userService.signOut(token);

        SignOutResponse response = new SignOutResponse();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
