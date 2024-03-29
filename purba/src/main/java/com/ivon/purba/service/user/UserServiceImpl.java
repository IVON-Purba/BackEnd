package com.ivon.purba.service.user;

import com.ivon.purba.domain.User;
import com.ivon.purba.dto.userController.SignUpRequest;
import com.ivon.purba.exception.exceptions.InvalidPhoneNumberPatternException;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import com.ivon.purba.exception.exceptions.UserAlreadyExistException;
import com.ivon.purba.exception.exceptions.UserNotFoundException;
import com.ivon.purba.repository.UserRepository;
import com.ivon.purba.service.security.RedisService;
import com.ivon.purba.service.serviceInterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository  userRepository;
    private final RedisService redisService;
    private static final long SIGN_OUT_TOKEN_VALIDITY = 24 * 60 * 60;

    // 회원가입
    @Override
    public void signUp(SignUpRequest request) {
        User user = createUser(request);
        validateDuplicationUser(user);
        userRepository.save(user);
    }

    // 로그인
    @Override
    public Long signIn(String phoneNumber) {
        User user = getUserByPhoneNumber(phoneNumber);
        return user.getId();
    }

    //로그아웃
    public void signOut(String token) {
        String cleanedToken = token.substring(7);
        redisService.setData(cleanedToken, "logout", SIGN_OUT_TOKEN_VALIDITY);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        return userOptional.orElseThrow(() -> new UserNotFoundException("해당 회원이 존재하지 않습니다."));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 ID로 회원를 조회할 수 없습니다."));
    }

    private User createUser(SignUpRequest request) {
        User user = new User();

        user.setName(request.getName());

        String phoneNumber = request.getPhoneNumber();
        checkPhoneNumberPattern(phoneNumber);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    private static void checkPhoneNumberPattern(String phoneNumber) {
        String phoneNumberPattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

        if (!phoneNumber.matches(phoneNumberPattern)) {
            throw new InvalidPhoneNumberPatternException("폰 번호 형식이 올바르지 않습니다.");
        }
    }

    private void validateDuplicationUser(User user) {
        userRepository.findByPhoneNumber(user.getPhoneNumber())
                .ifPresent(u -> {
                    throw new UserAlreadyExistException("이미 존재하는 유저입니다.");
                });
    }
}
