package org.npeonelove.authservice.client;

import org.npeonelove.authservice.model.profile.Profile;
import org.npeonelove.authservice.model.profile.ProfileCredentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "profile-client", url = "http://localhost:8084/profile")
public interface ProfileClient {

    @GetMapping(value = "/get-profile-by-email")
    Profile getProfile(@RequestParam("email") String email);

    @PostMapping(value = "/create-profile")
    void createProfile(@ModelAttribute ProfileCredentials profile,
                       @RequestHeader("Authorization") String token);

}
