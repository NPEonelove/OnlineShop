package org.npeonelove.cartservice.controller;


import org.npeonelove.cartservice.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request);
    }

}
