package org.npeonelove.catalogservice.exception.product;

public class ProductPhotoNotExistsException extends RuntimeException {
    public ProductPhotoNotExistsException(String message) {
        super(message);
    }
}
