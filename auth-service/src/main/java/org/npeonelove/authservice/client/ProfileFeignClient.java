package org.npeonelove.authservice.client;

import org.npeonelove.authservice.model.profile.Profile;
import org.npeonelove.authservice.dto.profile.ProfileCredentialsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "profile-service", url = "http://localhost:8084/profile")
@FeignClient(name = "profile-service")
public interface ProfileFeignClient {

    @GetMapping(value = "/profile/get-profile-by-email")
    Profile getProfile(@RequestParam("email") String email);

    @PostMapping(value = "/profile/create-profile")
    void createProfile(@RequestBody ProfileCredentialsDTO profile,
                       @RequestHeader("Authorization") String token);

}
