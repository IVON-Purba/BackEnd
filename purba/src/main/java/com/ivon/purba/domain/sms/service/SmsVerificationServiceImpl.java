package com.ivon.purba.domain.sms.service;

import com.ivon.purba.config.jwt.utils.JwtUtil;
import com.ivon.purba.config.jwt.dto.JwtToken;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyRequest;
import com.ivon.purba.domain.sms.service.interfaces.SmsStorageService;
import com.ivon.purba.domain.sms.service.interfaces.SmsVerificationService;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsVerificationServiceImpl implements SmsVerificationService {

    private final SmsStorageService smsStorageService;

    @Override
    public JwtToken verifyCode(SmsServiceVerifyRequest request) {
        String phoneNumber = request.getToPhoneNumber();
        String inputCodeHash = HashingUtility.toSHA256(request.getValidateCode());
        String storedCodeHash = smsStorageService.findVerificationCode(phoneNumber);

        //provide JwtToken
        if (storedCodeHash.equals(inputCodeHash)) {
            JwtToken token = JwtUtil.generateToken(phoneNumber);
            String refreshToken = token.getRefreshToken();
            smsStorageService.saveRefreshToken(phoneNumber, refreshToken);
            return token;
        }

        throw new ResourceNotFoundException("해당 인증번호가 일치하지 않습니다.");
    }
}
