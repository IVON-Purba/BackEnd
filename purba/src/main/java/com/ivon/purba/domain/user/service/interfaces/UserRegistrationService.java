package com.ivon.purba.domain.user.service.interfaces;

import com.ivon.purba.domain.user.dto.SignUpRequest;

public interface UserRegistrationService {
    void registerUser(SignUpRequest request);
}
