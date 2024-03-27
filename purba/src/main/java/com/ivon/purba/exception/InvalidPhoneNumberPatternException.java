package com.ivon.purba.exception;

public class InvalidPhoneNumberPatternException extends RuntimeException {
    public InvalidPhoneNumberPatternException() {}
    public InvalidPhoneNumberPatternException(String message) {
        super(message);
    }

}

