package com.example.userservice.userservice.Service;

import com.example.userservice.userservice.ErrorHandling.ApiError;
import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Models.Role;
import com.example.userservice.userservice.Models.User;
import com.example.userservice.userservice.Repositories.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleService {
    private final RoleRepository _roleRepository;
    private final RoleValidationService _validationService;

    public RoleService(RoleRepository roleRepository, RoleValidationService _validationService) {
        this._roleRepository = roleRepository;
        this._validationService = _validationService;
    }

    public ResponseEntity<List<Role>> findAllRoles() {
        return new ResponseEntity<>(this._roleRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Role> findRoleById(Integer id) throws InvalidRequestException, UserNotFoundException {
        this._validationService.validateId(id);
        Optional<Role> role = this._roleRepository.findById(id);
        this._validationService.validateObject(role);
        return new ResponseEntity(role.get(), HttpStatus.OK);
    }

    public ResponseEntity<Role> saveRole(Role role) throws InvalidRequestException, UserNotFoundException {
        try {
            findRoleById(role.getRoleId());
            return new ResponseEntity("User already exists.", HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            this._validationService.validateRoleProperties(role);
            Role newRole = this._roleRepository.save(role);
            return new ResponseEntity(newRole, HttpStatus.OK);
        }
    }

    public ResponseEntity<User> updateExistingRole(Integer id, Role role) throws InvalidRequestException, UserNotFoundException {
        this._validationService.validateId(id);
        this.findRoleById(id);
        role.setRoleId(id);
        Role updatedRole = this._roleRepository.save(role);
        return new ResponseEntity(updatedRole, HttpStatus.OK);
    }

    public ApiError  deleteRole(Integer id) throws InvalidRequestException, UserNotFoundException {
        this._validationService.validateId(id);
        this.findRoleById(id);
        this._roleRepository.deleteById(id);
        ApiError apiError = new ApiError(HttpStatus.OK, "User successfully deleted. !!! ");
        return apiError;
    }

}


