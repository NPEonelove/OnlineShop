package org.npeonelove.catalogservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ProductErrorResponse {
    String message;
    int status;
    Date timestamp;
}
