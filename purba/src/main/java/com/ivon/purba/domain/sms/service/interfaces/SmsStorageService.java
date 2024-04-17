package com.ivon.purba.domain.sms.service.interfaces;

public interface SmsStorageService {
    void saveVerificationCode(String phoneNumber, String code);

    String findVerificationCode(String phoneNumber);

    void saveRefreshToken(String phoneNumber, String refreshToken);
}
