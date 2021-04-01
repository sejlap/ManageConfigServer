package com.example.rentservice.rentservice.Models;

import com.example.rentservice.rentservice.Enums.StateEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class RealEstate {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer realEstateId;
    @NotBlank
    private String name;
    private Double price;
    private String address;
    private String country;
    private String city;
    private String description;
    private boolean isReservated;
    private Date dateFrom;
    @FutureOrPresent
    private Date dateTo;

    @JsonBackReference(value="name")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
    private StateEnum state;

    public Integer getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(Integer realEstateId) {
        this.realEstateId = realEstateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsReservated() {
        return isReservated;
    }

    public void setIsReservated(boolean isReservated) {
        this.isReservated = isReservated;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public com.example.rentservice.rentservice.Models.User getUser() {
        return user;
    }

    public void setUser(com.example.rentservice.rentservice.Models.User user) {
        this.user = user;
    }
    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public RealEstate() {
    }

    public RealEstate(String name, Double price, String address, String country, String city, String description, boolean isReservated, Date dateFrom, Date dateTo, User user, StateEnum state) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.country = country;
        this.city = city;
        this.description = description;
        this.isReservated = isReservated;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.user = user;
        this.state = state;
    }

}