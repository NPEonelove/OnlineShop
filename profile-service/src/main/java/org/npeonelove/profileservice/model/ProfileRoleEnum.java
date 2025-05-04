package org.npeonelove.profileservice.model;

import lombok.Getter;

@Getter
public enum ProfileRoleEnum {
    ADMIN("admin"),
    USER("user");

    private final String value;

    ProfileRoleEnum(String value) {
        this.value = value;
    }
}
