package com.ivon.purba.service.user;

import com.ivon.purba.domain.User;
import com.ivon.purba.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class UserUnitTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("DB에 회원 저장")
    public void saveUserToDB() {
        // given
        String phoneNumber = generateRandomPhoneNumber();
        User user = new User();
        user.setPhoneNumber(phoneNumber);

        //when
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByPhoneNumber(phoneNumber);

        //then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(foundUser.get().getId()).isNotNull();
    }

    private String generateRandomPhoneNumber() {
        int middle = ThreadLocalRandom.current().nextInt(1000, 10000);
        int last = ThreadLocalRandom.current().nextInt(1000, 10000);
        return String.format("010-%04d-%04d", middle, last);
    }
}
