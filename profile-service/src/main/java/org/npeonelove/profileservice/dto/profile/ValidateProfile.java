package org.npeonelove.profileservice.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateProfile {
    private String email;
    private String password;
}
