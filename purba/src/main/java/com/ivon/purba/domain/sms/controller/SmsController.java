package com.ivon.purba.domain.sms.controller;

import com.ivon.purba.domain.sms.dto.SmsServiceSendRequest;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyRequest;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyResponse;
import com.ivon.purba.domain.sms.service.SmsServiceImpl;
import com.ivon.purba.domain.security.dto.JwtToken;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SmsController {
    private final SmsServiceImpl smsService;

    @PostMapping("sms/send")
    public ResponseEntity<SingleMessageSentResponse> sendVerificationCode(@RequestBody SmsServiceSendRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(smsService.sendVerificationCode(request));
    }

    @PostMapping("sms/verify")
    public ResponseEntity<SmsServiceVerifyResponse> verify(@RequestBody SmsServiceVerifyRequest request) {
        JwtToken token = smsService.verifyCode(request);

        return ResponseEntity.ok(new SmsServiceVerifyResponse(token));
    }
}
