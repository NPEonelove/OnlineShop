package org.npeonelove.authservice.security;

import lombok.RequiredArgsConstructor;
import org.npeonelove.authservice.client.ProfileFeignClient;
import org.npeonelove.authservice.dto.jwt.JwtAuthenticationDTO;
import org.npeonelove.authservice.dto.jwt.RefreshTokenDTO;
import org.npeonelove.authservice.model.profile.Profile;
import org.npeonelove.authservice.dto.profile.ProfileCredentialsDTO;
import org.npeonelove.authservice.model.profile.ProfileRoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProfileFeignClient profileFeignClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationDTO signIn(ProfileCredentialsDTO profileCredentialsDTO) throws AuthenticationException {
        Profile profile = getProfileByCredentials(profileCredentialsDTO);
        return jwtService.generateAuthToken(profile.getEmail(), profile.getRole());
    }

    public JwtAuthenticationDTO signUp(ProfileCredentialsDTO profile) {
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        JwtAuthenticationDTO jwtDTO = jwtService.generateAuthToken(profile.getEmail(), ProfileRoleEnum.USER.toString());
        profileFeignClient.createProfile(profile, "Bearer " + jwtDTO.getToken());
        return jwtDTO;
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

    private Profile getProfileByCredentials(ProfileCredentialsDTO profileCredentialsDTO) throws AuthenticationException {
        Profile profile = profileFeignClient.getProfile(profileCredentialsDTO.getEmail());
        if (passwordEncoder.matches(profileCredentialsDTO.getPassword(), profile.getPassword())) {
            return profile;
        } else {
            throw new AuthenticationException("Invalid password");
        }
    }

    private Profile getProfileByEmail(String email) {
        return profileFeignClient.getProfile(email);
    }
}
