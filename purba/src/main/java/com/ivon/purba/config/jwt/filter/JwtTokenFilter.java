package com.ivon.purba.config.jwt.filter;

import com.ivon.purba.domain.refreshToken.service.AuthenticationService;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import com.ivon.purba.redis.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/user/signUp") || requestURI.equals("/sms/send") || requestURI.equals("/sms/verify")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            unauthorizedResponse(response, "인증 토큰이 없거나 유효하지 않습니다. 전화번호 인증이 필요합니다.");
            return;
        }

        String token = authorizationHeader.substring(7);
        Date tokenExpiration = AuthenticationService.getExpiration(token, secretKey);
        String isLogout = redisUtil.getData(token);

        if (tokenExpiration.before(new Date()) || !ObjectUtils.isEmpty(isLogout)) {
            unauthorizedResponse(response, "토큰 유효기간이 만료되었습니다. 전화번호 인증이 필요합니다.");
            return;
        }

        String phoneNumber = AuthenticationService.getLoginPhoneNumber(token, secretKey);
        User loginUser = userService.getUserByPhoneNumber(phoneNumber);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getId(), null, List.of(new SimpleGrantedAuthority(loginUser.getPhoneNumber())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private void unauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(message);
    }
}
