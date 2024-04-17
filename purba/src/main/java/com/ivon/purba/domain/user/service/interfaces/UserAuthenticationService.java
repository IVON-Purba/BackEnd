package com.ivon.purba.domain.user.service.interfaces;

public interface UserAuthenticationService {
    Long signIn(String phoneNumber);

    void signOut(String token);
}
