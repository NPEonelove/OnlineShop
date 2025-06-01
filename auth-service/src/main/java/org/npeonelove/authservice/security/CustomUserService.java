package org.npeonelove.authservice.security;

import lombok.RequiredArgsConstructor;
import org.npeonelove.authservice.client.ProfileFeignClient;
import org.npeonelove.authservice.model.profile.Profile;
import org.npeonelove.authservice.dto.profile.ProfileCredentialsDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final ProfileFeignClient profileFeignClient;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileFeignClient.getProfile(username);
        if (profile.equals(new Profile())) {
            return new CustomUserDetails(null);
        }
        ProfileCredentialsDTO profileCredentialsDTO = new ProfileCredentialsDTO();
        profileCredentialsDTO.setEmail(profile.getEmail());
        profileCredentialsDTO.setPassword(profile.getPassword());
        return new CustomUserDetails(profileCredentialsDTO);
    }
}
