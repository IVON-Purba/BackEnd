package com.ivon.purba.security;

import com.ivon.purba.service.serviceInterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
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
    private final RedisTemplate<String, Object> redisTemplate;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/signUp").permitAll()
                        .requestMatchers("/user/signIn").permitAll()
                        .requestMatchers("/sms/send").permitAll()
                        .requestMatchers("/**").authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(userService, secretKey, redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
