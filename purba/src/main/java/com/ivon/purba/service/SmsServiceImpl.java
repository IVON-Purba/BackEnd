package com.ivon.purba.service;

import com.ivon.purba.dto.jwt.JwtToken;
import com.ivon.purba.dto.smsController.SmsServiceSendRequest;
import com.ivon.purba.dto.smsController.SmsServiceVerifyRequest;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import com.ivon.purba.security.JwtTokenUtil;
import com.ivon.purba.service.serviceInterface.SmsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${app.myPhoneNumber}")
    private static String fromPhoneNumber;

    private DefaultMessageService messageService;

    private final RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    protected void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    @Override
    public SingleMessageSentResponse sendVerificationCode(SmsServiceSendRequest request) throws NoSuchAlgorithmException {
        String phoneNumber = request.getTo();
        String verificationCode = makeVerificationCode();

        try {
            saveVerificationCode(phoneNumber, verificationCode);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("인증 코드 저장 중 알고리즘 예외 발생");
        }

        Message message = setMessageForm(phoneNumber, verificationCode);
        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    @Override
    public JwtToken verifyCode(SmsServiceVerifyRequest request) throws NoSuchAlgorithmException {
        String phoneNumber = request.getPhoneNumber();

        String inputCodeHash = toSHA256(request.getValidateCode());
        String storedCodeHash = findVerificationCode(phoneNumber);

        if (storedCodeHash.equals(inputCodeHash)) {
            JwtToken token = JwtTokenUtil.createToken(phoneNumber);
            String refreshToken = token.getRefreshToken();

            //3h 3m
            long expireTimeSeconds = 1000 * 60 * 61 * 3;

            redisTemplate.opsForValue().set("refreshToken:" + phoneNumber, refreshToken, expireTimeSeconds);
            return token;
        }

        throw new ResourceNotFoundException("해당 인증번호가 일치하지 않습니다.");
    }

    private String makeVerificationCode() {
        return String.valueOf((int) (Math.random() * 8999) + 1000);
    }

    private static Message setMessageForm(String to, String verificationCode) {
        Message message = new Message();
        message.setFrom(fromPhoneNumber);
        message.setTo(to);
        message.setText("문자 인증 번호입니다.\n" + verificationCode);
        return message;
    }

    private void saveVerificationCode(String phoneNumber, String code) throws NoSuchAlgorithmException {
        try {
            String hashCode = toSHA256(code);
            ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
            valueOps.set("인증코드:" + phoneNumber, hashCode, 330, TimeUnit.SECONDS);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("인증 코드 해싱 중 오류 발생", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Redis 작업 중 오류 발생", e);
        }
    }

    private String findVerificationCode(String phoneNumber) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        String code = (String) valueOps.get("인증코드:" + phoneNumber);

        if (code == null) {
            throw new ResourceNotFoundException("인증 코드가 유효하지 않거나 만료되었습니다.");
        }

        return code;
    }

    private String toSHA256(String base) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
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
