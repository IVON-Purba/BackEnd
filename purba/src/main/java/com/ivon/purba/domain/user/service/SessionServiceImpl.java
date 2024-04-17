package com.ivon.purba.domain.user.service;

import com.ivon.purba.domain.security.service.RedisService;
import com.ivon.purba.domain.user.service.interfaces.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final RedisService redisService;
    private static final long SIGN_OUT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Override
    public void terminateSession(String token) {
        String cleanedToken = token.substring(7);
        redisService.setData(cleanedToken, "logout", SIGN_OUT_TOKEN_VALIDITY);
    }
}
