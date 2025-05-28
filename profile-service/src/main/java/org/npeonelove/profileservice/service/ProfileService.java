package org.npeonelove.profileservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.profileservice.client.MediaClient;
import org.npeonelove.profileservice.dto.profile.EditProfile;
import org.npeonelove.profileservice.dto.profile.GetProfile;
import org.npeonelove.profileservice.dto.profile.ProfileCredentials;
import org.npeonelove.profileservice.dto.profile.ValidateProfile;
import org.npeonelove.profileservice.exception.profile.ProfileDoesNotExistException;
import org.npeonelove.profileservice.exception.profile.ProfileNotEditedException;
import org.npeonelove.profileservice.exception.profile.ProfileValidateException;
import org.npeonelove.profileservice.exception.security.PermissionException;
import org.npeonelove.profileservice.model.Profile;
import org.npeonelove.profileservice.model.ProfileRoleEnum;
import org.npeonelove.profileservice.repository.ProfileRepository;
import org.npeonelove.profileservice.security.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;
    private final JwtService jwtService;
    private final String s3Directory = "profile/profilePhoto";

    // для первичной регистрации пользователя по email и password
    @Transactional
    public void createProfile(ProfileCredentials createProfile) {
        Profile profile = modelMapper.map(createProfile, Profile.class);
//        if (profileRepository.existsByEmail(profile.getEmail())) {
//            throw new ProfileNotCreatedException("Profile already exists");
//        }
        profile.setRegTime(new Timestamp(System.currentTimeMillis()));
        profile.setRole(ProfileRoleEnum.USER.getValue());
        profileRepository.save(profile);
    }

    // получение профиля по id
    public GetProfile getProfile(int id) {
        Profile profile = profileRepository.findProfileById(id);
        if (profile == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        }
        return modelMapper.map(profile, GetProfile.class);
    }

    // удаление профиля по id
    @Transactional
    public void deleteProfile(int id) {
        Profile profile = profileRepository.findProfileById(id);
        if (profile == null) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        } else if (!jwtService.getEmailFromSecurityContext().equals(profile.getEmail())) {
            throw new PermissionException("You don't have the rights to edit this user");
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
        Optional<Profile> profile = profileRepository.findProfileByEmail(validateProfile.getEmail());
        if (profile.isEmpty()) {
            throw new ProfileDoesNotExistException("Profile does not exist");
        } else if (!profile.get().getPassword().equals(validateProfile.getPassword())) {
            throw new ProfileValidateException("Passwords do not match");
        } else {
            return true;
        }
    }

    public Profile getProfileByEmail(String email) {
        Optional<Profile> profile = profileRepository.findProfileByEmail(email);
        if (profile.isEmpty()) {
            return new Profile();
//            throw new ProfileDoesNotExistException("Profile does not exist");
        }
        return profile.orElse(null);
    }
}











