package com.example.userservice.userservice.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer UserId;
    @Column (nullable = false)
    @NotBlank(message = "First name is  required")
    private String FirstName;
    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    private String LastName;
    @NotBlank(message = "Emailis required")
    private String email;
    @Column (nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password should be between 8 and 30 characters")
    private String Password;
    @Column (nullable = false)
    @NotBlank(message = "Address is required")
    private String Address;
    @Column (nullable = false)
    @NotBlank(message = "Country is required")
    private String Country;
    @Column (nullable = false)
    @NotBlank(message = "City is required")
    private String City;
    @Column (nullable = false)
    @NotBlank(message = "Phone is required")
    private String Phone;
    @Column (nullable = false)
    private Date DateOfBirth;
    @JsonBackReference(value="name")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    //@JsonIgnore
    private Role Role;

    public User(String firstName, String lastName, String email, String password, String address, String country, String city, String phone, Date dateOfBirth) {
        FirstName = firstName;
        LastName = lastName;
        this.email = email;
        Password = password;
        Address = address;
        Country = country;
        City = city;
        Phone = phone;
        DateOfBirth = dateOfBirth;

    }

    public User() {
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public com.example.userservice.userservice.Models.Role getRole() {
        return Role;
    }

    public void setRole(com.example.userservice.userservice.Models.Role role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId=" + UserId +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Email='" + email + '\'' +
                ", Password='" + Password + '\'' +
                ", Address='" + Address + '\'' +
                ", Country='" + Country + '\'' +
                ", City='" + City + '\'' +
                ", Phone='" + Phone + '\'' +
                ", DateOfBirth=" + DateOfBirth +
                ", Role=" + Role +
                '}';
    }
}
