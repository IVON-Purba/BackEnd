package com.ivon.purba.domain.refreshToken.controller;

import com.ivon.purba.domain.refreshToken.dto.RefreshTokenRequest;
import com.ivon.purba.domain.refreshToken.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            String newAccessToken = authenticationService.validateRefreshToken(request, authorizationHeader);
            return ResponseEntity.ok().body("New Access Token: " + newAccessToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }
    }
}
