package com.ivon.purba.service.security;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.jwt.JwtToken;
import com.ivon.purba.dto.jwt.RefreshTokenRequest;
import com.ivon.purba.service.serviceInterface.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtTokenService {

    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    //5min
    private static final long ACCESS_TOKEN_VALIDITY = 5 * 60 * 1000;
    //30min
    private static final long REFRESH_TOKEN_VALIDITY = 30 * 60 * 1000;

    @Value("${SECRET_KEY}")
    private String SIGNING_KEY;

    public JwtToken generateToken(String phoneNumber) {
        String accessToken = generateAccessToken(phoneNumber);
        String refreshToken = generateRefreshToken();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateAccessToken(String phoneNumber) {
        Claims claims = Jwts.claims();
        claims.put("phoneNumber", phoneNumber);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY * 3))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public String refreshToken(RefreshTokenRequest request) {
        User user = userService.getUserById(request.getUserId());
        String phoneNumber = user.getPhoneNumber();

        boolean isValid = validateRefreshToken(phoneNumber, request.getRefreshToken());

        if (!isValid) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        return generateAccessToken(phoneNumber);
    }

    private boolean validateRefreshToken(String phoneNumber, String refreshToken) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String storedToken = (String) valueOperations.get("refreshToken:" + phoneNumber);
        return refreshToken.equals(storedToken);
    }

    public String getLoginPhoneNumber(String token) {
        return extractClaims(token, SIGNING_KEY).get("phoneNumber").toString();
    }

    public Date getExpiration(String token) {
        return extractClaims(token, SIGNING_KEY).getExpiration();
    }

    private Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}