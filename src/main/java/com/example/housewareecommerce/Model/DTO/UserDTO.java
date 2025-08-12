package com.example.housewareecommerce.Model.DTO;

import java.util.Date;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private Long statusId; // ID của StatusEntity

    // Getter & Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}

