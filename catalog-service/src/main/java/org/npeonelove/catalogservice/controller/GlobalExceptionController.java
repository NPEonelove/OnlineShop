package org.npeonelove.catalogservice.controller;

import org.npeonelove.catalogservice.exception.ErrorResponse;
import org.npeonelove.catalogservice.exception.category.*;
import org.npeonelove.catalogservice.exception.photo.PhotoNotExistsException;
import org.npeonelove.catalogservice.exception.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        status.value(),
                        ex.getMessage(),
                        new Date(),
                        request.getDescription(false)
                ),
                status
        );
    }

    // Product exceptions
    @ExceptionHandler(ProductNotCreatedException.class)
    public ResponseEntity<ErrorResponse> handleProductNotCreated(ProductNotCreatedException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ProductAlreadyExistsException.class, ProductNotEditedException.class})
    public ResponseEntity<ErrorResponse> handleProductConflictExceptions(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({ProductNotExistsException.class, ProductPhotoNotExistsException.class})
    public ResponseEntity<ErrorResponse> handleProductNotFoundExceptions(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    // Category exceptions
    @ExceptionHandler({CategoryNotCreatedException.class, CategoryNotEditedException.class, CategoryAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleCategoryConflictExceptions(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(CategoryNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotExistsException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    // Photo exceptions
    @ExceptionHandler(PhotoNotExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhotoNotFound(PhotoNotExistsException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    // Global catch-all handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        return buildErrorResponse(
                ex,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }
}