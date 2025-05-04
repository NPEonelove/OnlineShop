package org.npeonelove.profileservice.controller;

import org.npeonelove.profileservice.exception.ProfileErrorResponse;
import org.npeonelove.profileservice.exception.profile.ProfileNotCreatedException;
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
}
