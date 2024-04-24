package com.ivon.purba.domain.user.service;

import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.repository.UserRepository;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import com.ivon.purba.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("해당 회원이 존재하지 않습니다."));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 회원이 존재하지 않습니다."));
    }
}
