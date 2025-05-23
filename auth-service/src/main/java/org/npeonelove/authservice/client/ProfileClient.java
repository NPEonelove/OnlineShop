package org.npeonelove.authservice.client;

import org.npeonelove.authservice.model.Profile;
import org.npeonelove.authservice.model.ProfileCredentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "profile-client", url = "http://localhost:8084")
public interface ProfileClient {

//    // возвращает http статус что все ок (ex: "OK")
//    @DeleteMapping(value = "/media")
//    String deleteMedia(@RequestParam("keys") String[] keys);

    @GetMapping(value = "/profile")
    Profile getProfile(@RequestParam("email") String email);

    @PostMapping(value = "/profile")
    void createProfile(@ModelAttribute ProfileCredentials profile);

}
