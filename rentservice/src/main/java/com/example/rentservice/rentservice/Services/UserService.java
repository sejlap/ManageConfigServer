package com.example.rentservice.rentservice.Services;

import com.example.rentservice.rentservice.ErrorHandling.InvalidRequestException;
import com.example.rentservice.rentservice.ErrorHandling.ObjectNotFoundException;
import com.example.rentservice.rentservice.Models.User;
import com.example.rentservice.rentservice.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //final - can't be extended
    private final UserRepository _userRepository;
    private final ValidationService _validationService;

    public UserService(UserRepository userRepository, ValidationService _validationService) {
        _userRepository = userRepository;
        this._validationService = _validationService;
    }

    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity(_userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<User> findUserById(Integer id) throws InvalidRequestException, ObjectNotFoundException {
        this._validationService.validateId(id);

        Optional<User> user = _userRepository.findById(id);
        if(user.isPresent()){
            return new ResponseEntity(user, HttpStatus.OK);
        }
        else{
            throw new ObjectNotFoundException("There is no user with the ID "+id);
        }
    }
    public ResponseEntity<User> findUserByEmail(String email) throws InvalidRequestException, ObjectNotFoundException {
        try{
            this._validationService.validateEmail(email);
            Optional<User> user = this._userRepository.findByEmail(email);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(InvalidRequestException ex){
            return new ResponseEntity("Email is invalid. Message: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<User> saveUser(User user) throws InvalidRequestException {
        try{
            this._validationService.validateUserProperties(user);
            User newRE = this._userRepository.save(user);
            return new ResponseEntity(newRE, HttpStatus.OK);
        }
        catch(InvalidRequestException ex){
            return new ResponseEntity("Fail to save user. Message: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<User> updateExistingUser(Integer id, User user) throws InvalidRequestException, ObjectNotFoundException {
        try {
            this._validationService.validateId(id);

            this.findUserById(id);

            var updatedUser = this._userRepository.save(new User(user));
            return new ResponseEntity(updatedUser, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity("Fail to update user. Message: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity deleteUser(Integer id) throws InvalidRequestException, ObjectNotFoundException {
        this._validationService.validateId(id);

        this.findUserById(id);

        this._userRepository.deleteById(id);
        return new ResponseEntity("User successfully deleted.", HttpStatus.OK);
    }
}