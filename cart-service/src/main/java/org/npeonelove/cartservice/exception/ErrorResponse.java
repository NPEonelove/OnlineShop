package org.npeonelove.cartservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private Date timestamp;
    private String details;

}
