package com.ivon.purba.domain.sms.service.interfaces;

import com.ivon.purba.domain.sms.dto.SmsServiceSendRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsSenderService {
    SingleMessageSentResponse sendVerificationCode(SmsServiceSendRequest request);
}
