package org.npeonelove.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.npeonelove.authservice.client.ProfileClient;
import org.npeonelove.authservice.model.JwtAuthenticationDTO;
import org.npeonelove.authservice.model.Profile;
import org.npeonelove.authservice.model.ProfileCredentials;
import org.npeonelove.authservice.model.RefreshTokenDTO;
import org.npeonelove.authservice.security.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final ProfileClient profileClient;
    private final UserService userService;

    @GetMapping
    public Profile getProfile(@RequestParam("email") String email) {
        return profileClient.getProfile(email);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerProfile(@RequestBody ProfileCredentials profile) {
        return ResponseEntity.ok(userService.createUser(profile));
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
