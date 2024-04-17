package com.ivon.purba.domain.sms.service;

import com.ivon.purba.domain.sms.dto.SmsServiceSendRequest;
import com.ivon.purba.domain.sms.service.interfaces.SmsSenderService;
import com.ivon.purba.domain.sms.service.interfaces.SmsStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsSenderServiceImpl implements SmsSenderService {

    private final SmsStorageService smsStorageService;

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${phoneNumber}")
    private String fromPhoneNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    protected void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    @Override
    public SingleMessageSentResponse sendVerificationCode(SmsServiceSendRequest request) {
        String phoneNumber = request.getToPhoneNumber();
        String verificationCode = makeVerificationCode();

        smsStorageService.saveVerificationCode(phoneNumber, verificationCode);

        Message message = setMessageForm(phoneNumber, verificationCode);
        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    private Message setMessageForm(String to, String verificationCode) {
        Message message = new Message();
        message.setFrom(fromPhoneNumber);
        message.setTo(to);
        message.setText("Purba 애플리케이션 문자 인증 번호입니다.\n" + verificationCode);
        return message;
    }

    private String makeVerificationCode() {
        return String.valueOf((int) (Math.random() * 8999) + 1000);
    }
}
