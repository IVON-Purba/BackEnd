package com.ivon.purba.domain.user.service;

import com.ivon.purba.domain.user.dto.SignUpRequest;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.repository.UserRepository;
import com.ivon.purba.domain.user.service.interfaces.UserRegistrationService;
import com.ivon.purba.domain.user.service.validator.PhoneNumberValidator;
import com.ivon.purba.domain.user.service.validator.UserDuplicationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRepository userRepository;
    private final PhoneNumberValidator phoneNumberValidator;
    private final UserDuplicationValidator duplicationValidator;

    @Override
    public void registerUser(SignUpRequest request) {
        String phoneNumber = phoneNumberValidator.validate(request.getPhoneNumber());
        User user = new User(request.getName(), phoneNumber);
        duplicationValidator.validateDuplicationUser(phoneNumber);
        userRepository.save(user);
    }
}
