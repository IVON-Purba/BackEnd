package com.ivon.purba.domain.user.service;


import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.service.interfaces.SessionService;
import com.ivon.purba.domain.user.service.interfaces.UserAuthenticationService;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserService userService;
    private final SessionService sessionService;

    @Override
    public Long signIn(String phoneNumber) {
        User user = userService.getUserByPhoneNumber(phoneNumber);
        return user.getId();
    }

    @Override
    public void signOut(String token) {
        sessionService.terminateSession(token);
    }
}
