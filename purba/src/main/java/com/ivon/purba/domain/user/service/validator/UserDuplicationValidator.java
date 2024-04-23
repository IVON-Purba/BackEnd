package com.ivon.purba.domain.user.service.validator;

import com.ivon.purba.domain.user.repository.UserRepository;
import com.ivon.purba.exception.exceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDuplicationValidator {
    private final UserRepository userRepository;

    public void validateDuplicationUser(String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(u -> {
                    throw new UserAlreadyExistException("이미 존재하는 유저입니다.");
                });
    }
}
