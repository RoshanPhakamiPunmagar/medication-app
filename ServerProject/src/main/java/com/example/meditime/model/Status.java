package com.example.meditime.model;
/**
 * Simple class representing a login status with a string value.
 */
public class Status {
    private String status;

    // Default constructor
    public Status() {
    }

    // Constructor with parameter
    public Status(String status) {
        this.status = status;
    }

    // Getter
    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }
}