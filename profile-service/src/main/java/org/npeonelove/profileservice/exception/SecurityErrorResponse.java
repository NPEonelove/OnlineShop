package org.npeonelove.profileservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class SecurityErrorResponse {
    String message;
    Date timestamp;
}
