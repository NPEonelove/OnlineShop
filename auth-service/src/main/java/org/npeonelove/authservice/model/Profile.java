package org.npeonelove.authservice.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Profile {

    private long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String photoLink;

    private Timestamp regTime;

    private String role;

}
