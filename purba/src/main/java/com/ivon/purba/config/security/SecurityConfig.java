package com.ivon.purba.config.security;

import com.ivon.purba.config.jwt.filter.JwtTokenFilter;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import com.ivon.purba.redis.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static java.lang.System.getenv;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final UserService userService;
    private static final String secretKey = getenv().get("SECRET_KEY");
    private final RedisUtil redisUtil;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/signUp").permitAll()
                        .requestMatchers("/user/signIn").permitAll()
                        .requestMatchers("/sms/send").permitAll()
                        .requestMatchers("/sms/verify").permitAll()
                        .requestMatchers("/**").authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(userService, secretKey, redisUtil), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
