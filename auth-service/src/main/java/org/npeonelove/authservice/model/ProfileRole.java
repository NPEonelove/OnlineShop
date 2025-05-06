package org.npeonelove.authservice.model;

import lombok.Getter;

@Getter
public enum ProfileRole {
    ADMIN("admin"),
    USER("user");

    private final String value;

    ProfileRole(String value) {
        this.value = value;
    }
}
