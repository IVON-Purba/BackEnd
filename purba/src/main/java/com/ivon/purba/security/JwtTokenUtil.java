package com.ivon.purba.security;

import com.ivon.purba.dto.jwt.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.System.getenv;

public class JwtTokenUtil {
    public static JwtToken createToken(String phoneNumber) {
        Claims claims = Jwts.claims();
        claims.put("phoneNumber", phoneNumber);
        String key = getenv().get("SECRET_KEY");

        //1hour
        long expireTimeMs = 1000 * 60 * 60;

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs * 3))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static String getLoginPhoneNumber(String token, String secretKey) {
        return extractClaims(token, secretKey).get("phoneNumber").toString();
    }

    public static Date getExpiration(String token, String secretKey) {
        return extractClaims(token, secretKey).getExpiration();
    }

    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}