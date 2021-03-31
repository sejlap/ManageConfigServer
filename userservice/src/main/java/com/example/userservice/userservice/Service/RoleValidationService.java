package com.example.userservice.userservice.Service;
import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Models.Role;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class RoleValidationService {

    public RoleValidationService() {
    }

    public void validateId(Integer id) throws InvalidRequestException {
        if (id == null || id < 1) {
            throw new InvalidRequestException("Received Id is not valid.");
        }
    }
    public <T> void validateObject(Optional<T> entity) throws UserNotFoundException {
        if (!entity.isPresent()) {
            throw new UserNotFoundException("User with received ID was not found.");
        }
    }

    public void validateRoleProperties(Role role) throws InvalidRequestException {
        List<String> nullProperties = new ArrayList<String>();
        List<String> invalidProperties = new ArrayList<String>();

        if (role.getName() == null || role.getName().isEmpty()) {
            nullProperties.add("Name");
        } else if (!role.getName().toLowerCase().matches("^[a-zA-Z]+( ?[a-zA-Z])*$")) {
            invalidProperties.add("Name");
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
