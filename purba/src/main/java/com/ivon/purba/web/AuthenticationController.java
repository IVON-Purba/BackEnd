package com.ivon.purba.web;

import com.ivon.purba.dto.jwt.RefreshTokenRequest;
import com.ivon.purba.service.security.JwtTokenService;
import com.ivon.purba.service.serviceInterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenService tokenService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok().body("New Access Token: " + tokenService.refreshToken(request));
    }
}
