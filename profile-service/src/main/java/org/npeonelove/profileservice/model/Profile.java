package org.npeonelove.profileservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profiles_id_seq")
    @SequenceGenerator(name = "profiles_id_seq", sequenceName = "profiles_id_seq")
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "photo_link")
    private String photoLink;

    @Column(name = "reg_time", nullable = false)
    private Timestamp regTime;

    @Column(name = "role", nullable = false)
    private String role;

}
