package org.npeonelove.authservice.model.profile;

import lombok.Getter;

@Getter
public enum ProfileRoleEnum {

    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    ProfileRoleEnum(String value) {
        this.value = value;
    }
}
