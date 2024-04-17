package com.ivon.purba.domain.refreshToken.service;

import com.ivon.purba.redis.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {
    private final RedisUtil redisUtil;

    public boolean validateRefreshToken(String phoneNumber, String refreshToken) {
        String storedToken = redisUtil.getData("refreshToken:" + phoneNumber);
        return refreshToken.equals(storedToken);
    }
}
