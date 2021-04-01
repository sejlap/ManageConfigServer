package com.example.rentservice.rentservice.ErrorHandling;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(String error) {
        super(error);
    }
}