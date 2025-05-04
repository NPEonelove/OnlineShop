package org.npeonelove.profileservice.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProfile {

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect email")
    private String email;

    @NotBlank(message = "Enter your password")
    private String password;

    @NotBlank(message = "Enter your first name")
    private String firstName;

    @NotBlank(message = "Enter your last name")
    private String lastName;

}
