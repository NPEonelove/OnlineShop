package org.npeonelove.catalogservice.controller;

import org.npeonelove.catalogservice.exception.CategoryErrorResponse;
import org.npeonelove.catalogservice.exception.PhotoErrorResponse;
import org.npeonelove.catalogservice.exception.ProductErrorResponse;
import org.npeonelove.catalogservice.exception.category.CategoryAlreadyExistsException;
import org.npeonelove.catalogservice.exception.category.CategoryNotCreatedException;
import org.npeonelove.catalogservice.exception.category.CategoryNotEditedException;
import org.npeonelove.catalogservice.exception.category.CategoryNotExistsException;
import org.npeonelove.catalogservice.exception.photo.PhotoNotExistsException;
import org.npeonelove.catalogservice.exception.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionController {

    //product
    @ExceptionHandler(ProductNotCreatedException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotCreatedException(ProductNotCreatedException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotCreatedException(ProductAlreadyExistsException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), new Date()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotExistsException(ProductNotExistsException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), new Date()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotEditedException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotExistsException(ProductNotEditedException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), new Date()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductPhotoNotExistsException.class)
    public ResponseEntity<ProductErrorResponse> handleProductNotExistsException(ProductPhotoNotExistsException ex) {
        return new ResponseEntity<>(new ProductErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), new Date()), HttpStatus.NOT_FOUND);
    }

    //category
    @ExceptionHandler(CategoryNotCreatedException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryNotCreatedException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), new Date()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotExistsException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryNotExistsException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), new Date()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryAlreadyExistsException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), new Date()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotEditedException.class)
    public ResponseEntity<CategoryErrorResponse> handleProductNotExistsException(CategoryNotEditedException ex) {
        return new ResponseEntity<>(new CategoryErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), new Date()), HttpStatus.CONFLICT);
    }

    //photo
    @ExceptionHandler(PhotoNotExistsException.class)
    public ResponseEntity<PhotoErrorResponse> handleProductNotExistsException(PhotoNotExistsException ex) {
        return new ResponseEntity<>(new PhotoErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), new Date()), HttpStatus.NOT_FOUND);
    }
}
