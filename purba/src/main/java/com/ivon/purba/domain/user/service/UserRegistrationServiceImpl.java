package com.ivon.purba.domain.user.service;

import com.ivon.purba.domain.user.dto.SignUpRequest;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.repository.UserRepository;
import com.ivon.purba.domain.user.service.interfaces.UserRegistrationService;
import com.ivon.purba.exception.exceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRepository userRepository;

    @Override
    public void registerUser(SignUpRequest request) {
        User user = createUser(request);
        validateDuplicationUser(user);
        userRepository.save(user);
    }

    private User createUser(SignUpRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        return user;
    }

    private void validateDuplicationUser(User user) {
        userRepository.findByPhoneNumber(user.getPhoneNumber()).ifPresent(u -> {
            throw new UserAlreadyExistException("이미 존재하는 유저입니다.");
        });
    }
}
