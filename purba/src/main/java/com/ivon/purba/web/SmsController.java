package com.ivon.purba.web;

import com.ivon.purba.dto.jwt.JwtToken;
import com.ivon.purba.dto.smsController.SmsServiceSendRequest;
import com.ivon.purba.dto.smsController.SmsServiceSendResponse;
import com.ivon.purba.dto.smsController.SmsServiceVerifyRequest;
import com.ivon.purba.dto.smsController.SmsServiceVerifyResponse;
import com.ivon.purba.service.sms.SmsServiceImpl;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
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
