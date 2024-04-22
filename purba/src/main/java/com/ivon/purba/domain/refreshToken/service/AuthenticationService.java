package com.ivon.purba.domain.refreshToken.service;

import com.ivon.purba.config.jwt.utils.JwtUtil;
import com.ivon.purba.domain.refreshToken.dto.RefreshTokenRequest;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public static String getLoginPhoneNumber(String token, String secretKey) {
        return extractClaims(token, secretKey).get("phoneNumber").toString();
    }

    // 밝급된 Token이 만료 시간이 지났는지 체크
    public static Date getExpiration(String token, String secretKey) {
        return extractClaims(token, secretKey).getExpiration();
    }

    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
