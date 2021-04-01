package ServiceTests;

import com.example.rentservice.rentservice.ErrorHandling.InvalidRequestException;
import com.example.rentservice.rentservice.ErrorHandling.ObjectNotFoundException;
import com.example.rentservice.rentservice.Models.User;
import com.example.rentservice.rentservice.Services.ValidationService;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {

    private ValidationService _validationService;

    ValidationServiceTest() {
        _validationService = new ValidationService();
    }

    @Test
    public void validateId_ShouldThrowInvalidRequestException() {
        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            _validationService.validateId(0);
        });

        String expectedMessage = "Received Id is not valid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateId_ShouldNotThrowException_WhenIdIsValid() {
        assertDoesNotThrow(() -> {
            _validationService.validateId(2);
        });
    }

    @Test
    public void validateObject_ShouldThrowUserNotFoundException() {
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            _validationService.validateObject(Optional.empty());
        });
        String expectedMessage = "Object was not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateObject_ShouldNotThrowException_WhenObjectIsNotNull() {
        var user = new User("TestFirstName", "TestLastName", "email@email.com", new Date(), "123456", null);
        assertDoesNotThrow(() -> {
            _validationService.validateObject(Optional.of(user));
        });
    }

    //First case - missing required properties (e.g. first and last name, email)
    @Test
    public void validateUserProperties_ShouldThrowInvalidRequestException_1() {
        User user = new User("", "", "email@email.com", new Date(), "123456", null);

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            _validationService.validateUserProperties(user);
        });

        String expectedMessage = "Properties that must be provided: First Name, Last Name.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    //Second case - wrong format
    @Test
    public void validateUserProperties_ShouldThrowInvalidRequestException_2() {
        User user = new User("TestFirstName", "TestLastName", "email", new Date(), "!", null);

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            _validationService.validateUserProperties(user);
        });
        String expectedMessage = "Wrong format: Email, Phone.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}