package org.npeonelove.profileservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.profileservice.client.MediaClient;
import org.npeonelove.profileservice.dto.profile.CreateProfile;
import org.npeonelove.profileservice.model.Profile;
import org.npeonelove.profileservice.model.ProfileRoleEnum;
import org.npeonelove.profileservice.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;

    public void createProfile(CreateProfile createProfile) {
        Profile profile = modelMapper.map(createProfile, Profile.class);
        profile.setRegTime(new Timestamp(System.currentTimeMillis()));
        profile.setRole(ProfileRoleEnum.USER.getValue());
        profileRepository.save(profile);
    }

    public void createProfile(CreateProfile createProfile, MultipartFile file) {
        Profile profile = modelMapper.map(createProfile, Profile.class);
        profile.setRegTime(new Timestamp(System.currentTimeMillis()));
        profile.setRole(ProfileRoleEnum.USER.getValue());



        profileRepository.save(profile);
    }

}
