package com.ivon.purba.service.serviceInterface;

import com.ivon.purba.dto.jwt.JwtToken;
import com.ivon.purba.dto.smsController.SmsServiceSendRequest;
import com.ivon.purba.dto.smsController.SmsServiceVerifyRequest;
import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

import java.security.NoSuchAlgorithmException;

public interface SmsService {

    SingleMessageSentResponse sendVerificationCode(SmsServiceSendRequest request) throws NoSuchAlgorithmException;

    JwtToken verifyCode(SmsServiceVerifyRequest request) throws NoSuchAlgorithmException;
}
