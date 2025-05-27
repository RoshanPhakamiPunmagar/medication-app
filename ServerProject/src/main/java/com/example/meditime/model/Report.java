// Amy Wickham 12178502
// File: Report.java
// Description: Represents a report written by a carer about a client, including notes and a timestamp of creation.

package com.example.meditime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter // Lombok automatically generates getters and setters for all fields
public class Report {

    // Primary key for the Report entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    // Many-to-one relationship: the carer who authored the report
    @ManyToOne
    private User carer;

    // Many-to-one relationship: the client (patient) the report is about
    @ManyToOne
    private Client client;

    // Free-text notes written by the carer
    private String notes;

    // Timestamp indicating when the report was created
    private LocalDateTime dateCreated;

    // Default constructor that sets the creation date to now
    public Report() {
        this.dateCreated = LocalDateTime.now();
    }

    // Lombok's @Getter and @Setter generate all necessary accessor methods
}
