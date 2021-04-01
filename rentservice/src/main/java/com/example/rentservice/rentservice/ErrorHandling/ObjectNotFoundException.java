package com.example.rentservice.rentservice.ErrorHandling;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String error) {
        super(error);
    }
}