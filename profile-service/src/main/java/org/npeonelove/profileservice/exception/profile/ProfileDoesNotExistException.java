package org.npeonelove.profileservice.exception.profile;

public class ProfileDoesNotExistException extends RuntimeException {
    public ProfileDoesNotExistException(String message) {
        super(message);
    }
}
