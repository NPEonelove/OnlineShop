package org.npeonelove.catalogservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PhotoErrorResponse {
    String message;
    Date timestamp;
    public PhotoErrorResponse(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
