package org.npeonelove.catalogservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CategoryErrorResponse {
    String message;
    Date timestamp;
}
