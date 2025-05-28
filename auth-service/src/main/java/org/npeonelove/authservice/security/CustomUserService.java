package org.npeonelove.authservice.security;

import lombok.RequiredArgsConstructor;
import org.npeonelove.authservice.client.ProfileClient;
import org.npeonelove.authservice.model.profile.Profile;
import org.npeonelove.authservice.model.profile.ProfileCredentials;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final ProfileClient profileClient;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileClient.getProfile(username);
        if (profile.equals(new Profile())) {
            return new CustomUserDetails(null);
        }
        ProfileCredentials profileCredentials = new ProfileCredentials();
        profileCredentials.setEmail(profile.getEmail());
        profileCredentials.setPassword(profile.getPassword());
        return new CustomUserDetails(profileCredentials);
//        return new CustomUserDetails(modelMapper.map(profile, ProfileCredentials.class));
//        return modelMapper.map(profile, CustomUserDetails.class);
    }
}
