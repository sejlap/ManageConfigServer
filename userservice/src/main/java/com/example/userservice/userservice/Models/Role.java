package com.example.userservice.userservice.Models;

import com.example.userservice.userservice.Models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
//@JsonIgnoreProperties({"HibernateLazyInitializer", "handler"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer RoleId;
    private String Name;

    @JsonManagedReference(value = "name")
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Role")
    private List<User> Users;
    public Role(Integer roleId, String name) {
        RoleId = roleId;
        Name = name;
    }
    public Role() {
    }

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}