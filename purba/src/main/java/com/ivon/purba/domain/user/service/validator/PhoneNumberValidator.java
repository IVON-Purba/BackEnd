package com.ivon.purba.domain.user.service.validator;

import com.ivon.purba.exception.exceptions.InvalidPhoneNumberPatternException;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberValidator {
    public String validate(String phoneNumber) {
        String phoneNumberPattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        if (!phoneNumber.matches(phoneNumberPattern)) {
            throw new InvalidPhoneNumberPatternException("폰 번호 형식이 올바르지 않습니다.");
        }
        return phoneNumber;
    }
}
