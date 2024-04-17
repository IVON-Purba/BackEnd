package com.ivon.purba.domain.user.service;

import com.ivon.purba.domain.user.service.interfaces.SessionService;
import com.ivon.purba.redis.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final RedisUtil redisUtil;
    private static final long SIGN_OUT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Override
    public void terminateSession(String token) {
        String cleanedToken = token.substring(7);
        redisUtil.setData(cleanedToken, "logout", SIGN_OUT_TOKEN_VALIDITY);
    }
}
