package org.npeonelove.profileservice.controller;

import org.npeonelove.profileservice.exception.ErrorResponse;
import org.npeonelove.profileservice.exception.profile.*;
import org.npeonelove.profileservice.exception.security.PermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

    // Общий метод для создания ошибок профиля
    private ResponseEntity<ErrorResponse> buildProfileErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        status.value(),
                        "Profile Error: " + ex.getMessage(),
                        new Date(),
                        getRequestPath(request)
                ),
                status
        );
    }

    // Общий метод для security ошибок
    private ResponseEntity<ErrorResponse> buildSecurityErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        status.value(),
                        "Security Error: " + ex.getMessage(),
                        new Date(),
                        request.getDescription(false)
                ),
                status
        );
    }

    // Извлекаем путь запроса из WebRequest
    private String getRequestPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    // Групповая обработка ошибок профиля
    @ExceptionHandler({
            ProfileNotCreatedException.class,
            ProfileDoesNotExistException.class,
            ProfileNotEditedException.class,
            ProfileValidateException.class
    })
    public ResponseEntity<ErrorResponse> handleProfileExceptions(RuntimeException ex, WebRequest request) {
        return buildProfileErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    // Обработка ошибок безопасности
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ErrorResponse> handlePermissionException(PermissionException ex, WebRequest request) {
        return buildSecurityErrorResponse(ex, HttpStatus.FORBIDDEN, request);
    }

    // Обработка всех непредвиденных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error: " + ex.getMessage(),
                        new Date(),
                        getRequestPath(request)
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}