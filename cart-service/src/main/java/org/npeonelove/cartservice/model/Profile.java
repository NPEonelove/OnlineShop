package org.npeonelove.cartservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    private long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String photoLink;

    private Timestamp regTime;

    private String role;

    public long getId() {
        return this.id;
    }

}
