package org.npeonelove.profileservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.profileservice.dto.profile.EditProfile;
import org.npeonelove.profileservice.dto.profile.GetProfile;
import org.npeonelove.profileservice.dto.profile.ProfileCredentials;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
import org.npeonelove.profileservice.exception.profile.ProfileNotEditedException;
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
@Tag(name = "Управление профилями", description = "API для работы с профилями пользователей")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/create-profile")
    @Hidden
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

    @GetMapping("/get-profile")
    @Operation(
            summary = "Получить профиль (просто отправить авторизованный запрос)"
    )
    public ResponseEntity<GetProfile> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PatchMapping("/edit-profile-by-id/{id}")
    @Operation(
            summary = "Редактировать профиль по ID"
    )
    public ResponseEntity<HttpStatus> editProfile(
            @Parameter(description = "ID профиля", required = true)
            @PathVariable("id") int id,

            @Parameter(description = "Данные для обновления профиля")
            @ModelAttribute @Valid EditProfile editProfile,

            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append(". ");
            }
            throw new ProfileNotEditedException(errors.toString());
        }
        profileService.editProfile(id, editProfile);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete-profile-by-id/{id}")
    @Operation(
            summary = "Удалить профиль по ID"
    )
    public ResponseEntity<HttpStatus> deleteProfile(
            @Parameter(description = "ID профиля", required = true)
            @PathVariable("id") int id
    ) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get-profile-by-email")
    @Hidden
    public ResponseEntity<Profile> getProfileByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(profileService.getProfileByEmail(email));
    }
}
