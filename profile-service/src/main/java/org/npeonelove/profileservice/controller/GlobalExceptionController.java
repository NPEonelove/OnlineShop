package org.npeonelove.profileservice.controller;

import org.npeonelove.profileservice.exception.ProfileErrorResponse;
import org.npeonelove.profileservice.exception.SecurityErrorResponse;
import org.npeonelove.profileservice.exception.profile.ProfileDoesNotExistException;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
import org.npeonelove.profileservice.exception.profile.ProfileNotEditedException;
import org.npeonelove.profileservice.exception.profile.ProfileValidateException;
import org.npeonelove.profileservice.exception.security.PermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

    // Profile exceptions
    @ExceptionHandler(ProfileNotCreatedException.class)
    public ResponseEntity<ProfileErrorResponse> handleProfileNotCreatedException(ProfileNotCreatedException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileDoesNotExistException.class)
    public ResponseEntity<ProfileErrorResponse> handleProfileDoesNotExistException(ProfileDoesNotExistException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileNotEditedException.class)
    public ResponseEntity<ProfileErrorResponse> handleProfileNotEditedException(ProfileNotEditedException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileValidateException.class)
    public ResponseEntity<ProfileErrorResponse> handleProfileValidationException(ProfileValidateException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    // Security exception
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<SecurityErrorResponse> handlePermissionException(PermissionException ex) {
        return new ResponseEntity<>(new SecurityErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }
}
