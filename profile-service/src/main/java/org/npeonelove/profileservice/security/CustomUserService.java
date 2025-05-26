package org.npeonelove.profileservice.security;

import lombok.RequiredArgsConstructor;
import org.npeonelove.profileservice.dto.profile.ProfileCredentials;
import org.npeonelove.profileservice.model.Profile;
import org.npeonelove.profileservice.service.ProfileService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final ProfileService profileService;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileService.getProfileByEmail(username);
        ProfileCredentials profileCredentials = new ProfileCredentials();
        profileCredentials.setEmail(profile.getEmail());
        profileCredentials.setPassword(profile.getPassword());
        return new CustomUserDetails(profileCredentials);
    }
}
