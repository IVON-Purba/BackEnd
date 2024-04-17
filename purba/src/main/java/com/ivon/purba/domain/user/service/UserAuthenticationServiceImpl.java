package com.ivon.purba.domain.user.service;


import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.repository.UserRepository;
import com.ivon.purba.domain.user.service.interfaces.SessionService;
import com.ivon.purba.domain.user.service.interfaces.UserAuthenticationService;
import com.ivon.purba.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Override
    public Long signIn(String phoneNumber) {
        User user = getUserByPhoneNumber(phoneNumber);
        return user.getId();
    }

    @Override
    public void signOut(String token) {
        sessionService.terminateSession(token);
    }

    private User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("해당 회원이 존재하지 않습니다."));
    }
}
