package org.npeonelove.catalogservice.exception.photo;

public class PhotoNotExistsException extends RuntimeException {
    public PhotoNotExistsException(String message) {
        super(message);
    }
}
