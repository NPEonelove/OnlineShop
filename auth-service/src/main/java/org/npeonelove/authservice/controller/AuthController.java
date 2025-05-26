package org.npeonelove.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.npeonelove.authservice.model.jwt.JwtAuthenticationDTO;
import org.npeonelove.authservice.model.jwt.RefreshTokenDTO;
import org.npeonelove.authservice.model.profile.ProfileCredentials;
import org.npeonelove.authservice.security.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationDTO> signUp(@RequestBody ProfileCredentials profile) {
        return ResponseEntity.ok(userService.signUp(profile));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDTO> signIn(@RequestBody ProfileCredentials profile) throws AuthenticationException {
        return ResponseEntity.ok(userService.signIn(profile));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) throws AuthenticationException {
        return ResponseEntity.ok(userService.refreshToken(refreshTokenDTO));
    }
}
