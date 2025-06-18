package org.npeonelove.cartservice.client;

import org.npeonelove.cartservice.model.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "profile-service")
public interface ProfileFeignClient {

//    @GetMapping("/users/{id}")
//    User getUserById(@PathVariable("id") Long id);

//    @PatchMapping("/product/edit-product-by-id/{id}")
    @GetMapping("/profile/get-profile-by-email")
    Profile getProfileByEmail(@RequestParam("email") String email);

}
