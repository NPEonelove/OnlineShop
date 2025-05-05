package org.npeonelove.profileservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.profileservice.client.MediaClient;
import org.npeonelove.profileservice.dto.profile.CreateProfile;
import org.npeonelove.profileservice.dto.profile.EditProfile;
import org.npeonelove.profileservice.dto.profile.GetProfile;
import org.npeonelove.profileservice.exception.profile.ProfileDoesNotExistException;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
import org.npeonelove.profileservice.exception.profile.ProfileNotEditedException;
import org.npeonelove.profileservice.model.Profile;
import org.npeonelove.profileservice.model.ProfileRoleEnum;
import org.npeonelove.profileservice.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;

    @Transactional
    public void createProfile(CreateProfile createProfile) {
        Profile profile = modelMapper.map(createProfile, Profile.class);
        if (profileRepository.existsById(profile.getId())) {
            throw new ProfileNotCreatedException("Profile already exists");
        }
        profile.setRegTime(new Timestamp(System.currentTimeMillis()));
        profile.setRole(ProfileRoleEnum.USER.getValue());
        profileRepository.save(profile);
    }

    @Transactional
    public void createProfile(CreateProfile createProfile, MultipartFile file) {
        Profile profile = modelMapper.map(createProfile, Profile.class);
        if (profileRepository.existsById(profile.getId())) {
            throw new ProfileNotCreatedException("Profile already exists");
        }
        profile.setRegTime(new Timestamp(System.currentTimeMillis()));
        profile.setRole(ProfileRoleEnum.USER.getValue());
        profile.setPhotoLink(mediaClient.uploadMedia("profile/profilePhoto",
                Collections.singletonList(file).toArray(new MultipartFile[0])).getFirst());
        profileRepository.save(profile);
    }

    public GetProfile getProfile(int id) {
        Profile profile = profileRepository.findProfileById(id);
        if (profile == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        }
        return modelMapper.map(profile, GetProfile.class);
    }

    @Transactional
    public void deleteProfile(int id) {
        Profile profile = profileRepository.findProfileById(id);
        if (profile == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        }
        // TODO: переписать логику удаления фото после удаления профиля в media-service,
        //  а так же применить ее в catalog-service
        profileRepository.delete(profile);
    }

    @Transactional
    public void editProfile(int id, EditProfile editProfile, MultipartFile file) {
        if (profileRepository.findProfileById(id) == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        } else if (!profileRepository.findProfileById(id).getRole().equals(editProfile.getEmail())) {
            throw new ProfileNotEditedException("Your email should be unique");
        } else {
            Profile profile = profileRepository.findProfileById(id);
            modelMapper.map(editProfile, profile);
            if (file == null) {
                profileRepository.save(profile);
            } else {
                // TODO: написать логику для редактировании фото в media-service,
                //  а так же применить ее в catalog-service
            }
        }
    }
}
