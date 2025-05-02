package org.npeonelove.catalogservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ProductErrorResponse {
    String message;
    Date timestamp;
    public ProductErrorResponse(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
