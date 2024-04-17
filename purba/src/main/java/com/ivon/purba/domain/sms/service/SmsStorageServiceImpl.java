package com.ivon.purba.domain.sms.service;

import com.ivon.purba.domain.security.service.RedisService;
import com.ivon.purba.domain.sms.service.interfaces.SmsStorageService;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class SmsStorageServiceImpl implements SmsStorageService {
    private final RedisService redisService;
    private static final long VERIFICATION_CODE_VALIDITY = 5 * 60;

    @Override
    public void saveVerificationCode(String phoneNumber, String code) {
        try {
            String hashCode = HashingUtility.toSHA256(code);
            redisService.setData("인증코드:" + phoneNumber, hashCode, VERIFICATION_CODE_VALIDITY);
        } catch (Exception e) {
            throw new RuntimeException("Redis 작업 중 오류 발생:" + e.getMessage());
        }
    }

    @Override
    public String findVerificationCode(String phoneNumber) {
        String code = redisService.getData("인증코드:" + phoneNumber);

        if (code == null) {
            throw new ResourceNotFoundException("인증 코드가 유효하지 않거나 만료되었습니다.");
        }
        return code;
    }

    @Override
    public void saveRefreshToken(String phoneNumber, String refreshToken) {
        redisService.setData("refreshToken:" + phoneNumber, refreshToken, VERIFICATION_CODE_VALIDITY);
    }
}
