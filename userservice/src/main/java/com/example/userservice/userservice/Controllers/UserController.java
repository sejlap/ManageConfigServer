package com.example.userservice.userservice.Controllers;
import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Service.UserService;
import com.example.userservice.userservice.Models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@Controller // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody User addNewUser (@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
    */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        this._userService = userService;
    }

    @GetMapping("/all")
    ResponseEntity<List<User>> findAllUsers() {
        return _userService.findAllUsers();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findUserById(@PathVariable(value = "id") Integer id)  throws InvalidRequestException, UserNotFoundException {
        return this._userService.findUserById(id);
    }

    @PostMapping("/add")
    ResponseEntity<User> addNewUser(@RequestBody User user) throws InvalidRequestException, UserNotFoundException   {
        return _userService.saveUser(user);
    }
    @PutMapping("/update/{id}")
    ResponseEntity<User> updateUser(@PathVariable(value = "id") Integer id, @RequestBody User user) throws InvalidRequestException, UserNotFoundException  {
        return this._userService.updateExistingUser(id, user);
    }
    @DeleteMapping("delete/{id}")
    ResponseEntity deleteUser(@PathVariable(value = "id") Integer id) throws InvalidRequestException, UserNotFoundException   {
        return this._userService.deleteUser(id);
    }
 @GetMapping(value = "", params = "Email")
    public ResponseEntity<User> UserByMail(@RequestParam("Email") String Email)  throws UserNotFoundException {
        return this._userService.findUserByEmail(Email);
    }
}



