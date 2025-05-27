// Amy Wickham 12178502
// File: Status.java
// Description: Represents a status string used in the MediTime system. This class may represent custom or general-purpose status values.

package com.example.meditime.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a generic status container.
 * It holds a single string value indicating a status.
 */
@Getter // Lombok generates a getter for the 'status' field
@Setter // Lombok generates a setter for the 'status' field
public class Status {

    /**
     * The status value (e.g., "Given", "Pending", "Missed").
     */
    private String status;

    /**
     * Default constructor.
     */
    public Status() {
    }

    /**
     * Constructor to initialize the status field.
     *
     * @param status the status string to set
     */
    public Status(String status) {
        this.status = status;
    }

    // No manual getter/setter needed due to Lombok annotations.
}
