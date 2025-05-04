package org.npeonelove.profileservice.model;

public enum ProfileRoleEnum {
    ADMIN("admin"),
    USER("user");

    private final String value;

    ProfileRoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
