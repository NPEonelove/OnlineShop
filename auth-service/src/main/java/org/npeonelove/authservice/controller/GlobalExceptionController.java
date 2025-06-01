package org.npeonelove.authservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.npeonelove.authservice.exception.AuthErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.util.Date;

@ControllerAdvice
@Hidden
public class GlobalExceptionController {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<AuthErrorResponse> handleException(AuthenticationException ex) {
        return new ResponseEntity<>(new AuthErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), new Date()), HttpStatus.UNAUTHORIZED);

    }

}
