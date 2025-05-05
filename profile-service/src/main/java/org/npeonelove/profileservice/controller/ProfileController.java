package org.npeonelove.profileservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.profileservice.dto.profile.CreateProfile;
import org.npeonelove.profileservice.dto.profile.GetProfile;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
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

    @PostMapping
    public ResponseEntity<HttpStatus> createProfile(@ModelAttribute @Valid CreateProfile createProfile, MultipartFile file,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProfileNotCreatedException(errors.toString());
        }
        if (file == null || file.isEmpty()) {
            profileService.createProfile(createProfile);
        } else {
            profileService.createProfile(createProfile, file);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProfile> getProfile(@PathVariable("id") int id) {
        return ResponseEntity.ok(profileService.getProfile(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable("id") int id) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
