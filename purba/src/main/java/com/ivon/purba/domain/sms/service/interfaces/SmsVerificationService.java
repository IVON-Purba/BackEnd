package com.ivon.purba.domain.sms.service.interfaces;

import com.ivon.purba.domain.security.dto.JwtToken;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyRequest;

public interface SmsVerificationService {
    JwtToken verifyCode(SmsServiceVerifyRequest request);
}
