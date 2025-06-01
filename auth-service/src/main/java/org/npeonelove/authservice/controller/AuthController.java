package org.npeonelove.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.npeonelove.authservice.dto.jwt.JwtAuthenticationDTO;
import org.npeonelove.authservice.dto.jwt.RefreshTokenDTO;
import org.npeonelove.authservice.dto.profile.ProfileCredentialsDTO;
import org.npeonelove.authservice.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API для аутентификации и регистрации")
public class AuthController {
    private final UserService userService;

    @Operation(
            summary = "Регистрация нового пользователя"
    )
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationDTO> signUp(@RequestBody ProfileCredentialsDTO profile) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(profile));
    }

    @Operation(
            summary = "Аутентификация пользователя"
    )
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDTO> signIn(@RequestBody ProfileCredentialsDTO profile) throws AuthenticationException {
        return ResponseEntity.ok(userService.signIn(profile));
    }

    @Operation(
            summary = "Обновление токена доступа"
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) throws AuthenticationException {
        return ResponseEntity.ok(userService.refreshToken(refreshTokenDTO));
    }
}
