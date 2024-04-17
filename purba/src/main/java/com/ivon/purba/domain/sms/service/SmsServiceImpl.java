package com.ivon.purba.domain.sms.service;

import com.ivon.purba.domain.security.dto.JwtToken;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import com.ivon.purba.domain.security.service.JwtTokenService;
import com.ivon.purba.domain.security.service.RedisService;
import com.ivon.purba.domain.sms.dto.SmsServiceSendRequest;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final JwtTokenService jwtTokenService;
    private final RedisService redisService;


    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${phoneNumber}")
    private String fromPhoneNumber;
    private static final long VERIFICATION_CODE_VALIDITY = 5 * 60;
    private static final long REFRESH_TOKEN_VALIDITY = 30 * 60;
    private DefaultMessageService messageService;

    @PostConstruct
    protected void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    @Override
    public SingleMessageSentResponse sendVerificationCode(SmsServiceSendRequest request) {
        String phoneNumber = request.getTo();
        String verificationCode = makeVerificationCode();

        saveVerificationCode(phoneNumber, verificationCode);

        Message message = setMessageForm(phoneNumber, verificationCode);
        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    @Override
    public JwtToken verifyCode(SmsServiceVerifyRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String inputCodeHash = toSHA256(request.getValidateCode());
        String storedCodeHash = findVerificationCode(phoneNumber);

        //provide JwtToken
        if (storedCodeHash.equals(inputCodeHash)) {
            JwtToken token = jwtTokenService.generateToken(phoneNumber);
            String refreshToken = token.getRefreshToken();
            redisService.setData("refreshToken:" + phoneNumber, refreshToken, REFRESH_TOKEN_VALIDITY);
            return token;
        }

        throw new ResourceNotFoundException("해당 인증번호가 일치하지 않습니다.");
    }

    private void saveVerificationCode(String phoneNumber, String code) {
        try {
            String hashCode = toSHA256(code);
            redisService.setData("인증코드:" + phoneNumber, hashCode, VERIFICATION_CODE_VALIDITY);
        } catch (Exception e) {
            throw new RuntimeException("Redis 작업 중 오류 발생:" + e.getMessage());
        }
    }

    private String findVerificationCode(String phoneNumber) {
        String code =  redisService.getData("인증코드:" + phoneNumber);

        if (code == null) {
            throw new ResourceNotFoundException("인증 코드가 유효하지 않거나 만료되었습니다.");
        }
        return code;
    }

    private String makeVerificationCode() {
        return String.valueOf((int) (Math.random() * 8999) + 1000);
    }

    private Message setMessageForm(String to, String verificationCode) {
        Message message = new Message();
        message.setFrom(fromPhoneNumber);
        message.setTo(to);
        message.setText("Purba 애플리케이션 문자 인증 번호입니다.\n" + verificationCode);
        return message;
    }

    private String toSHA256(String base)  {
        MessageDigest digest = null;
        try{
            digest = MessageDigest.getInstance("SHA-256");
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
