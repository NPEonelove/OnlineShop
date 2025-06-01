package org.npeonelove.profileservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.profileservice.dto.profile.*;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
import org.npeonelove.profileservice.exception.profile.ProfileNotEditedException;
import org.npeonelove.profileservice.exception.profile.ProfileValidateException;
import org.npeonelove.profileservice.model.Profile;
import org.npeonelove.profileservice.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/create-profile")
    public ResponseEntity<ProfileCredentials> createProfile(@RequestBody @Valid ProfileCredentials profileCredentials,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProfileNotCreatedException(errors.toString());
        }
        profileService.createProfile(profileCredentials);
        return ResponseEntity.ok(profileCredentials);
    }

    @GetMapping("/get-profile-by-id/{id}")
    public ResponseEntity<GetProfile> getProfile(@PathVariable("id") int id) {
        return ResponseEntity.ok(profileService.getProfile(id));
    }

    @PatchMapping("/edit-profile-by-id/{id}")
    public ResponseEntity<HttpStatus> editProfile(@PathVariable("id") int id, @ModelAttribute @Valid EditProfile editProfile,
                                                  MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProfileNotEditedException(errors.toString());
        }

        if (file == null || file.isEmpty()) {
            profileService.editProfile(id, editProfile);
        } else {
            profileService.editProfile(id, editProfile, file);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete-profile-by-id/{id}")
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable("id") int id) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get-profile-by-email")
    public ResponseEntity<Profile> getProfileByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(profileService.getProfileByEmail(email));
    }
}
