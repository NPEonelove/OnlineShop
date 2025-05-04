package org.npeonelove.profileservice.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class GetProfile {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String photoLink;
    private Timestamp regTime;
}
