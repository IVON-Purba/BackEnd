package com.ivon.purba.domain.sms.service.interfaces;

import com.ivon.purba.domain.sms.dto.SmsServiceSendRequest;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyRequest;
import com.ivon.purba.domain.security.dto.JwtToken;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

import java.security.NoSuchAlgorithmException;

public interface SmsService {

    SingleMessageSentResponse sendVerificationCode(SmsServiceSendRequest request) throws NoSuchAlgorithmException;

    JwtToken verifyCode(SmsServiceVerifyRequest request) throws NoSuchAlgorithmException;
}
