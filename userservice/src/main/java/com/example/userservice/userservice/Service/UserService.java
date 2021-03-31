package com.example.userservice.userservice.Service;
import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Models.User;
import com.example.userservice.userservice.Repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository _userRepository;
    private final UserValidationService _validationService;

    public UserService(UserRepository userRepository, UserValidationService _validationService) {
        this._userRepository = userRepository;
        this._validationService = _validationService;
    }

    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(this._userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<User> findUserById(Integer id) throws InvalidRequestException, UserNotFoundException {
        this._validationService.validateId(id);
        Optional<User> user = this._userRepository.findById(id);
        this._validationService.validateObject(user);
        return new ResponseEntity(user.get(), HttpStatus.OK);
    }
    public ResponseEntity<User> findUserByEmail(String email) throws  UserNotFoundException {
        Optional<User> user = this._userRepository.findByEmail(email);
        this._validationService.validateObject(user);
        return new ResponseEntity(user.get(), HttpStatus.OK);
    }
    public ResponseEntity<User> saveUser(User user) throws InvalidRequestException, UserNotFoundException {
        try {
            findUserByEmail(user.getEmail());
            return new ResponseEntity("User already exists.", HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            this._validationService.validateUserProperties(user);
            this._validationService.validatePassword(user.getPassword());
           // System.out.println(x);
            User newUser = this._userRepository.save(user);
            return new ResponseEntity(newUser, HttpStatus.OK);
        }
    }


    public ResponseEntity<User> updateExistingUser(Integer id, User user) throws InvalidRequestException, UserNotFoundException {
        this._validationService.validateId(id);
        this.findUserById(id);
        user.setUserId(id);
        this._validationService.validatePassword(user.getPassword());
        User updatedUser = this._userRepository.save(user);
        return new ResponseEntity(updatedUser, HttpStatus.OK);
    }

    public ResponseEntity deleteUser(Integer id) throws InvalidRequestException, UserNotFoundException {
        this._validationService.validateId(id);
        this.findUserById(id);
        this._userRepository.deleteById(id);
        return new ResponseEntity("User successfully deleted.", HttpStatus.OK);
    }
}
