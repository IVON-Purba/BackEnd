package com.ivon.purba.config.jwt.utils;

import com.ivon.purba.config.jwt.dto.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    @Value("${SECRET_KEY}")
    private static String SIGNING_KEY;

    private static final long ACCESS_TOKEN_VALIDITY = 300000;  // 5 minutes
    private static final long REFRESH_TOKEN_VALIDITY = 1800000; // 30 minutes

    public static JwtToken generateToken(String phoneNumber) {
        String accessToken = generateAccessToken(phoneNumber);
        String refreshToken = generateRefreshToken();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static String generateAccessToken(String phoneNumber) {
        Claims claims = Jwts.claims();
        claims.put("phoneNumber", phoneNumber);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    private static String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY * 3))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
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
