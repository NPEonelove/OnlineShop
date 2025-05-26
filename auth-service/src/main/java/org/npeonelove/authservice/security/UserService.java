package org.npeonelove.authservice.security;

import lombok.RequiredArgsConstructor;
import org.npeonelove.authservice.client.ProfileClient;
import org.npeonelove.authservice.model.jwt.JwtAuthenticationDTO;
import org.npeonelove.authservice.model.profile.Profile;
import org.npeonelove.authservice.model.profile.ProfileCredentials;
import org.npeonelove.authservice.model.jwt.RefreshTokenDTO;
import org.npeonelove.authservice.model.profile.ProfileRoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProfileClient profileClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationDTO signIn(ProfileCredentials profileCredentials) throws AuthenticationException {
        Profile profile = getProfileByCredentials(profileCredentials);
        return jwtService.generateAuthToken(profile.getEmail(), profile.getRole());
    }

    public JwtAuthenticationDTO signUp(ProfileCredentials profile) {
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        profileClient.createProfile(profile);
        return jwtService.generateAuthToken(profile.getEmail(), ProfileRoleEnum.USER.getValue());
    }

    public JwtAuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO) throws AuthenticationException {
        String refreshToken = refreshTokenDTO.getRefreshToken();
        if (refreshToken == null || !jwtService.validateToken(refreshToken)) {
            throw new AuthenticationException("Invalid refresh token");
        } else {
            Profile profile = getProfileByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(profile.getEmail(), profile.getRole(), refreshToken);
        }
    }

    private Profile getProfileByCredentials(ProfileCredentials profileCredentials) throws AuthenticationException {
        Profile profile = profileClient.getProfile(profileCredentials.getEmail());
        if (passwordEncoder.matches(profileCredentials.getPassword(), profile.getPassword())) {
            return profile;
        } else {
            throw new AuthenticationException("Invalid password");
        }
    }

    private Profile getProfileByEmail(String email) {
        return profileClient.getProfile(email);
    }
}
