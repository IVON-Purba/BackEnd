package com.ivon.purba.domain.sms.controller;

import com.ivon.purba.domain.sms.dto.SmsServiceSendRequest;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyRequest;
import com.ivon.purba.domain.sms.dto.SmsServiceVerifyResponse;
import com.ivon.purba.domain.sms.service.interfaces.SmsSenderService;
import com.ivon.purba.domain.sms.service.interfaces.SmsVerificationService;
import jakarta.validation.Valid;
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
    private final SmsSenderService sender;
    private final SmsVerificationService validator;

    @PostMapping("sms/send")
    public ResponseEntity<SingleMessageSentResponse> sendVerificationCode(@Valid @RequestBody SmsServiceSendRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sender.sendVerificationCode(request));
    }

    @PostMapping("sms/verify")
    public ResponseEntity<SmsServiceVerifyResponse> verify(@Valid @RequestBody SmsServiceVerifyRequest request) {
        return ResponseEntity.ok(new SmsServiceVerifyResponse(validator.verifyCode(request)));
    }
}
