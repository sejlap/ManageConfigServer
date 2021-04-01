package com.example.rentservice.rentservice.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;


@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;
    @Past
    private Date dateOfBirth;
    private String phone;

    @JsonManagedReference(value="name")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<RealEstate> realEstates;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<RealEstate> getRealEstates() {
        return realEstates;
    }

    public void setRealEstates(List<RealEstate> realEstates) {
        this.realEstates = realEstates;
    }
    public User() {
    }

    public User(User user){
        this.setUserId(user.userId);
        this.setPhone(user.phone);
        this.setEmail(user.email);
        this.setFirstName(user.firstName);
        this.setLastName(user.lastName);
        this.setDateOfBirth(user.dateOfBirth);
        this.setRealEstates(user.realEstates);
    }

    public User(String firstName, String lastName, String email, Date dateOfBirth, String phone, List<RealEstate> realEstates) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.realEstates = realEstates;
    }

}