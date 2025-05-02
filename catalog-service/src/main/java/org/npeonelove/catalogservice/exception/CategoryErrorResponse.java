package org.npeonelove.catalogservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CategoryErrorResponse {
    String message;
    Date timestamp;
    public CategoryErrorResponse(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
