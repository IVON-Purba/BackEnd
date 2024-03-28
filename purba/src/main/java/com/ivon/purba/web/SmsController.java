package com.ivon.purba.web;

import com.ivon.purba.dto.jwt.JwtToken;
import com.ivon.purba.dto.smsController.SmsServiceSendRequest;
import com.ivon.purba.dto.smsController.SmsServiceSendResponse;
import com.ivon.purba.dto.smsController.SmsServiceVerifyRequest;
import com.ivon.purba.dto.smsController.SmsServiceVerifyResponse;
import com.ivon.purba.security.JwtTokenUtil;
import com.ivon.purba.service.SmsServiceImpl;
import com.ivon.purba.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class SmsController {
    private final SmsServiceImpl smsService;

    @PostMapping("sms/send")
    public ResponseEntity<SmsServiceSendResponse> sendVerificationCode(@RequestBody SmsServiceSendRequest request) throws NoSuchAlgorithmException {
        smsService.sendVerificationCode(request);

        SmsServiceSendResponse response = new SmsServiceSendResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("sms/verify")
    public ResponseEntity<SmsServiceVerifyResponse> verify(@RequestBody SmsServiceVerifyRequest request) throws NoSuchAlgorithmException {
        JwtToken token = smsService.verifyCode(request);

        SmsServiceVerifyResponse response = new SmsServiceVerifyResponse(token);
        return ResponseEntity.ok(response);
    }
}
