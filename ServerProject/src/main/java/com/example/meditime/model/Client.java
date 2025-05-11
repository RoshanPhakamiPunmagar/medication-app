package com.example.meditime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    private Long clientId;
    private String name;
    private String dob;
    private String contactInfo;
    private Long carerUserId;

    // Getters and Setters
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Long getCarerUserId() {
        return carerUserId;
    }

    public void setCarerUserId(Long carerUserId) {
        this.carerUserId = carerUserId;
    }
}
