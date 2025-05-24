package org.npeonelove.profileservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.profileservice.client.MediaClient;
import org.npeonelove.profileservice.dto.profile.*;
import org.npeonelove.profileservice.exception.profile.ProfileDoesNotExistException;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
import org.npeonelove.profileservice.exception.profile.ProfileNotEditedException;
import org.npeonelove.profileservice.exception.profile.ProfileValidateException;
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
    private final String s3Directory = "profile/profilePhoto";

    @Transactional
    public void createProfile(ProfileCredentials createProfile) {
        Profile profile = modelMapper.map(createProfile, Profile.class);
        if (profileRepository.existsById(profile.getId())) {
            throw new ProfileNotCreatedException("Profile already exists");
        }
        profile.setRegTime(new Timestamp(System.currentTimeMillis()));
        profile.setRole(ProfileRoleEnum.USER.getValue());
        profileRepository.save(profile);
    }

    @Transactional
    public void createProfile(ProfileCredentials createProfile, MultipartFile file) {
        Profile profile = modelMapper.map(createProfile, Profile.class);
        if (profileRepository.existsById(profile.getId())) {
            throw new ProfileNotCreatedException("Profile already exists");
        }
        profile.setRegTime(new Timestamp(System.currentTimeMillis()));
        profile.setRole(ProfileRoleEnum.USER.getValue());
        profile.setPhotoLink(mediaClient.uploadMedia(s3Directory,
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
        if (profile.getPhotoLink() != null) {
            mediaClient.deleteMedia(new String[]{profile.getPhotoLink()});
        }
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
            if (file != null) {
                String photoLink = mediaClient.uploadMedia(s3Directory, Collections.singletonList(file)
                        .toArray(new MultipartFile[0])).getFirst();
                if (profile.getPhotoLink() != null) {
                    mediaClient.deleteMedia(new String[]{profile.getPhotoLink()});
                }
                profile.setPhotoLink(photoLink);
            }
            profileRepository.save(profile);
        }
    }

    @Transactional
    public void editProfile(int id, EditProfile editProfile) {
        if (profileRepository.findProfileById(id) == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        } else if (!profileRepository.findProfileById(id).getRole().equals(editProfile.getEmail())) {
            throw new ProfileNotEditedException("Your email should be unique");
        } else {
            Profile profile = profileRepository.findProfileById(id);
            modelMapper.map(editProfile, profile);
            profileRepository.save(profile);
        }
    }

    public Boolean validateProfileCredentials(ValidateProfile validateProfile) {
        Profile profile = profileRepository.findProfileByEmail(validateProfile.getEmail());
        if (profile == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        } else if (!profile.getPassword().equals(validateProfile.getPassword())) {
            throw new ProfileValidateException("Passwords do not match");
        } else {
            return true;
        }
    }

    public Profile getProfileByEmail(String email) {
        Profile profile = profileRepository.findProfileByEmail(email);
        if (profile == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        }
        return profile;
//        return modelMapper.map(profile, GetProfile.class);
    }
}











