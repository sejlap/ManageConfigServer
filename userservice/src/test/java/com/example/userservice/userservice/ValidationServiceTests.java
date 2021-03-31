package com.example.userservice.userservice;


import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Models.User;
import com.example.userservice.userservice.Service.UserValidationService;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTests {

    private UserValidationService validationService;

    ValidationServiceTests() {
        validationService = new UserValidationService();
    }

    @Test
    public void validateId_ShouldThrowInvalidRequestException() {
        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            validationService.validateId(0);
        });

        String expectedMessage = "Received Id is not valid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateId_ShouldNotThrowException_WhenIdIsValid() {
        assertDoesNotThrow(() -> {
            validationService.validateId(2);
        });
    }

    @Test
    public void validateObject_ShouldThrowUserNotFoundException() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            validationService.validateObject(Optional.empty());
        });
        String expectedMessage = "User with received ID was not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateObject_ShouldNotThrowException_WhenObjectIsNotNull() {
        User user = new User("Fname", "Lname", " ", "password", "address", "country", "city", "123456",  new Date());
        assertDoesNotThrow(() -> {
            validationService.validateObject(Optional.of(user));
        });
    }

    //First case - missing required properties (e.g. first and last name, email)
    @Test
    public void validateUserProperties_ShouldThrowInvalidRequestException_1() {
        User user = new User("", "", " ", "Password123!", "address", "country", "city", "123456",  new Date());

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            validationService.validateUserProperties(user);
            validationService.validatePassword(user.getPassword());
        });

        String expectedMessage = "Properties that must be provided: First Name, Last Name. Wrong format: Email.";
        String actualMessage = exception.getMessage();
        //System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    //Second case - wrong format
    @Test
    public void validateUserProperties_ShouldThrowInvalidRequestException_2() {
        User user =  new User("Sejla", "Pljakic", "1", "Password123!","Adresa1", "BIH", "Sarajevo", "sejla",new Date());

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            validationService.validateUserProperties(user);
        });
        String expectedMessage = "Wrong format: Email, Phone.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateUserProperties_ShouldNotThrowException_WhenUserIsValid() {
        User user = new User("Sejla", "Pljakic", "noviemail@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());

        assertDoesNotThrow(() -> {
            validationService.validatePassword(user.getPassword());
            validationService.validateUserProperties(user);
        });
    }
}