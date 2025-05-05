package org.npeonelove.profileservice.controller;

import org.npeonelove.profileservice.exception.ProfileErrorResponse;
import org.npeonelove.profileservice.exception.profile.ProfileDoesNotExistException;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
import org.npeonelove.profileservice.exception.profile.ProfileNotEditedException;
import org.npeonelove.profileservice.exception.profile.ProfileValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

    //profile
    @ExceptionHandler(ProfileNotCreatedException.class)
    public ResponseEntity<ProfileErrorResponse> handleProductNotCreatedException(ProfileNotCreatedException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileDoesNotExistException.class)
    public ResponseEntity<ProfileErrorResponse> handleProductNotCreatedException(ProfileDoesNotExistException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileNotEditedException.class)
    public ResponseEntity<ProfileErrorResponse> handleProductNotCreatedException(ProfileNotEditedException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileValidateException.class)
    public ResponseEntity<ProfileErrorResponse> handleProductNotCreatedException(ProfileValidateException ex) {
        return new ResponseEntity<>(new ProfileErrorResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }
}
