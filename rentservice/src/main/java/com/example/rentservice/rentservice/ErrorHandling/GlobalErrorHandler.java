package com.example.rentservice.rentservice.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ObjectNotFoundException.class})
    private ResponseEntity<ApiError> handleUserNotFoundException(ObjectNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({InvalidRequestException.class})
    private ResponseEntity<ApiError> handleInvalidRequestException(InvalidRequestException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }
}