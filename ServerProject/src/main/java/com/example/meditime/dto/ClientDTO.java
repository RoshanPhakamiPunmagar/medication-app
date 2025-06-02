package com.example.meditime.dto;
/**
 * Data Transfer Object for Client entity.
 *
 * Used to transfer client data across layers, encapsulating
 * client ID, name, date of birth, contact information, and assigned carer ID.
 */
public class ClientDTO {
    private Long clientId;
    private String name;
    private String dob;
    private String contact;
    private Long carerUserId;

    public ClientDTO(Long clientId, String name, String dob, String contact, Long carerUserId) {
        this.clientId = clientId;
        this.name = name;
        this.dob = dob;
        this.contact = contact;
        this.carerUserId = carerUserId;
    }
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getCarerUserId() {
        return carerUserId;
    }

    public void setCarerUserId(Long carerUserId) {
        this.carerUserId = carerUserId;
    }
}
