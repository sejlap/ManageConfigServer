package com.example.userservice.userservice.Controllers;

import com.example.userservice.userservice.ErrorHandling.ApiError;
import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Models.User;
import com.example.userservice.userservice.Repositories.RoleRepository;
import com.example.userservice.userservice.Models.Role;
import com.example.userservice.userservice.Service.RoleService;
import com.example.userservice.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@Controller // This means that this class is a Controller
@RequestMapping(path="/role") // This means URL's start with /demo (after Application path)
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody
    Role addNewRole (@RequestBody Role role ) {
        return roleRepository.save(role);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Role> getAllRoles() {
        // This returns a JSON or XML with the users
        return roleRepository.findAll();
    }*/



@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService _roleService;

    public RoleController(RoleService roleService) {
        this._roleService = roleService;
    }

    @GetMapping("/all")
    ResponseEntity<List<Role>> findAllRoles() {
        return _roleService.findAllRoles();
    }

    @GetMapping("/{id}")
    ResponseEntity<Role> findRoleById(@PathVariable(value = "id") Integer id)  throws InvalidRequestException, UserNotFoundException {
        return this._roleService.findRoleById(id);
    }

    @PostMapping("/add")
    ResponseEntity<Role> addNewRole(@RequestBody Role role) throws InvalidRequestException, UserNotFoundException   {
        return _roleService.saveRole(role);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<User> updateUser(@PathVariable(value = "id") Integer id, @RequestBody Role role ) throws InvalidRequestException, UserNotFoundException  {
        return this._roleService.updateExistingRole(id, role);
    }

    @DeleteMapping("delete/{id}")
    ApiError deleteRole(@PathVariable(value = "id") Integer id) throws InvalidRequestException, UserNotFoundException   {
        return this._roleService.deleteRole(id);
    }
}