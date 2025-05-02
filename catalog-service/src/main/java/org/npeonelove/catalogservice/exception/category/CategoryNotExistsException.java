package org.npeonelove.catalogservice.exception.category;

public class CategoryNotExistsException extends RuntimeException {
    public CategoryNotExistsException(String message) {
        super(message);
    }
}
