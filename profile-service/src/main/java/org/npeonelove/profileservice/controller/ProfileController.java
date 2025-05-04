package org.npeonelove.profileservice.controller;

import lombok.RequiredArgsConstructor;
import org.npeonelove.profileservice.service.ProfileService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


}
