package org.npeonelove.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class AuthErrorResponse {
    String message;
    int status;
    Date timestamp;
}
