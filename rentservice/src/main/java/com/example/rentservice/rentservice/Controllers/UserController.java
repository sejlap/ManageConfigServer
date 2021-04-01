package com.example.rentservice.rentservice.Controllers;

import com.example.rentservice.rentservice.ErrorHandling.InvalidRequestException;
import com.example.rentservice.rentservice.ErrorHandling.ObjectNotFoundException;
import com.example.rentservice.rentservice.Models.User;
import com.example.rentservice.rentservice.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class UserController {
    private UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @GetMapping(path="/all")
    ResponseEntity<List<User>> getAllUsers() {
        // This returns a JSON with the users
        return _userService.findAllUsers();
    }
    @GetMapping("/{id}")
    ResponseEntity<User> findUserById(@PathVariable(value = "id") Integer id) throws InvalidRequestException, ObjectNotFoundException {
        return this._userService.findUserById(id);
    }

    @GetMapping("findUserByEmail/{email}")
    ResponseEntity<User> findUserByEmail(@PathVariable(value = "email")String email) throws InvalidRequestException, ObjectNotFoundException {
        return this._userService.findUserByEmail(email);
    }

    @PostMapping("/add")
    ResponseEntity<User> addNewUser(@RequestBody User User) throws InvalidRequestException, ObjectNotFoundException {
        return _userService.saveUser(User);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<User> updateUser(@PathVariable(value = "id") Integer id, @RequestBody User User) throws InvalidRequestException, ObjectNotFoundException {
        return _userService.updateExistingUser(id, User);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity deleteUser(@PathVariable(value = "id") Integer id) throws InvalidRequestException, ObjectNotFoundException {
        return _userService.deleteUser(id);
    }
}