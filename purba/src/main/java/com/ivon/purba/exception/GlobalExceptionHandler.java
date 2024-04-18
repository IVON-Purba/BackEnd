package com.ivon.purba.exception;

import com.ivon.purba.exception.exceptions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            UserAlreadyExistException.class,
            PhotoSaveException.class,
            ResourceNotFoundException.class,
            AIAnalysisException.class,
            FileStorageException.class,
            InvalidFileNameException.class,
            InvalidPhoneNumberPatternException.class,
            DataAccessException.class,
            RuntimeException.class
    })
    public ResponseEntity<String> handleCustomException(RuntimeException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "오류가 발생했습니다." + e.getMessage();

        // 예외 유형에 따른 상태 및 메시지 설정
        if (e instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = e.getMessage() != null ? e.getMessage() : "회원을 찾을 수 없습니다.";
        } else if (e instanceof UserAlreadyExistException) {
            status = HttpStatus.CONFLICT;
            errorMessage = e.getMessage() != null ? e.getMessage() : "이미 존재하는 회원입니다.";
        } else if (e instanceof PhotoSaveException) {
            errorMessage = e.getMessage() != null ? e.getMessage() : "사진 저장에 실패했습니다.";
        } else if (e instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = e.getMessage() != null ? e.getMessage() : "리소스를 찾을 수 없습니다.";
        } else if (e instanceof AIAnalysisException) {
            errorMessage = e.getMessage() != null ? e.getMessage() : "AI 분석 중 오류가 발생했습니다.";
        } else if (e instanceof FileStorageException) {
            errorMessage = e.getMessage() != null ? e.getMessage() : "파일 저장에 실패했습니다.";
        } else if (e instanceof InvalidFileNameException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = e.getMessage() != null ? e.getMessage() : "유효하지 않은 파일 이름입니다.";
        } else if (e instanceof InvalidPhoneNumberPatternException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = e.getMessage() != null ? e.getMessage() : "폰 번호 형식이 올바르지 않습니다.";
        } else if (e instanceof DataAccessException) {
            errorMessage = e.getMessage() != null ? e.getMessage() : "데이터 저장에 문제가 생겼습니다.";
        } else if (e instanceof RuntimeException) {
            errorMessage = e.getMessage() != null ? e.getMessage() : "실행 중 오류 발생";
        }

        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        String errorMessage = "입출력 오류가 발생했습니다.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        String errorMessage = "잘못된 매개변수입니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<String> handleNoSuchAlgorithmException(NoSuchAlgorithmException e) {
        String errorMessage = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
