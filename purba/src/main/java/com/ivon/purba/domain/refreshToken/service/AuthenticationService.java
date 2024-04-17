package com.ivon.purba.domain.refreshToken.service;

import com.ivon.purba.config.jwt.utils.JwtUtil;
import com.ivon.purba.domain.refreshToken.dto.RefreshTokenRequest;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserService userService;
    private final TokenValidationService tokenValidationService;
    private final JwtUtil jwtUtil;

    public String validateRefreshToken(RefreshTokenRequest request, String authorizationHeader) {
        String phoneNumber = userService.getUserById(request.getUserId()).getPhoneNumber();
        String refreshToken = authorizationHeader.substring(7);
        boolean isValid = tokenValidationService.validateRefreshToken(phoneNumber, refreshToken);

        if (!isValid) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        return jwtUtil.generateAccessToken(phoneNumber);
    }
}
