package org.npeonelove.authservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.npeonelove.authservice.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.util.Date;

@ControllerAdvice
@Hidden
public class GlobalExceptionController {

    private static final String AUTH_PREFIX = "AUTH_ERROR: ";
    private static final String INTERNAL_PREFIX = "INTERNAL_ERROR: ";

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String prefix, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        status.value(),
                        prefix + ex.getMessage(),
                        new Date(),
                        request.getDescription(false).replace("uri=", "")
                ),
                status
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, AUTH_PREFIX, request);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_PREFIX, request);
    }
}