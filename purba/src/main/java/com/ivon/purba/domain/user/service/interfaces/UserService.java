package com.ivon.purba.domain.user.service.interfaces;

import com.ivon.purba.domain.user.entity.User;

public interface UserService {

    User getUserByPhoneNumber(String phoneNumber);

    User getUserById(Long id);
}
