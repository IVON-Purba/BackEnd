package com.ivon.purba.domain.sms.service.interfaces;

import com.ivon.purba.config.jwt.dto.JwtToken;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyRequest;

public interface SmsVerificationService {
    JwtToken verifyCode(SmsServiceVerifyRequest request);
}
