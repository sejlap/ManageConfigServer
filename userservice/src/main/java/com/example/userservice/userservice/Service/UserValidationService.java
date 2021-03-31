package com.example.userservice.userservice.Service;
import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Models.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.passay.*;

import java.util.Arrays;
import java.util.List;

@Service
public class UserValidationService {
    public UserValidationService() {
    }

    public void validateId(Integer id) throws InvalidRequestException {
        if (id == null || id < 1) {
            throw new InvalidRequestException("Received Id is not valid.");
        }
    }
    public void validatePassword(String password) throws InvalidRequestException  {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // at least 8 characters
                new LengthRule(8, 30),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),
                // no whitespace
                new WhitespaceRule(),
                // rejects passwords that contain a sequence of >= 5 characters alphabetical  (e.g. abcdef)
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                // rejects passwords that contain a sequence of >= 5 characters numerical   (e.g. 12345)
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)
        ));
        RuleResult result = validator.validate(new PasswordData(password));
        List<String> messages = validator.getMessages(result);
         if(messages.size()!=0)
             throw new InvalidRequestException("Response: " + messages);
    }
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    public static boolean validateEmail(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public <T> void validateObject(Optional<T> entity) throws UserNotFoundException {
        if (!entity.isPresent()) {
            throw new UserNotFoundException("User with received ID was not found.");
        }
    }


    public void validateUserProperties(User user) throws InvalidRequestException {
        List<String> nullProperties = new ArrayList<String>();
        List<String> invalidProperties = new ArrayList<String>();

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            nullProperties.add("First Name");
        } else if (!user.getFirstName().toLowerCase().matches("^[a-zA-Z]+( ?[a-zA-Z])*$")) {
            invalidProperties.add("First Name");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            nullProperties.add("Last Name");
        } else if (!user.getLastName().matches("^[a-zA-Z]+( ?[a-zA-Z])*$")) {
            invalidProperties.add("Last Name");
        }

        if (user.getDateOfBirth() == null) {
            nullProperties.add("Birth Date");
        } else if (user.getDateOfBirth().after(new Date())) {
            invalidProperties.add("Birth date");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            nullProperties.add("Email");
        } else if (!user.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            invalidProperties.add("Email");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) nullProperties.add("Password");

        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            nullProperties.add("Address");
        } else if (!user.getAddress().toLowerCase().matches("^[a-zA-Z0-9]+( ?[a-zA-Z0-9,.])*$")) {
            invalidProperties.add("Address");
        }
        if (user.getCountry() == null || user.getCountry().isEmpty()) {
            nullProperties.add("Country");
        } else if (!user.getCountry().toLowerCase().matches("^[a-zA-Z]+( ?[a-zA-Z])*$")) {
            invalidProperties.add("Country");
        }
        if (user.getCity() == null || user.getCity().isEmpty()) {
            nullProperties.add("City");
        } else if (!user.getCity().toLowerCase().matches("^[a-zA-Z]+( ?[a-zA-Z])*$")) {
            invalidProperties.add("City");
        }
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            nullProperties.add("Phone");
        } else if (!user.getPhone().toLowerCase().matches("[0-9]+")) {
            invalidProperties.add("Phone");
        }

        String result = nullProperties.size() > 0 ?
                "Properties that must be provided: " + String.join(", ", nullProperties) + ". " :
                "";

        if (invalidProperties.size() > 0) {
            result += "Wrong format: " + String.join(", ", invalidProperties) + ".";
        }

        if (!result.isEmpty()) {
            throw new InvalidRequestException(result);
        }
    }
}
