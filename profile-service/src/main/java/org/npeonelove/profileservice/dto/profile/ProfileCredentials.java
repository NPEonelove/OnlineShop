package org.npeonelove.profileservice.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCredentials {
    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect email")
    private String email;

    @NotBlank(message = "Enter your password")
    private String password;
}
