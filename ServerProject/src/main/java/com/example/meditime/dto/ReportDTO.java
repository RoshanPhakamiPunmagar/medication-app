// Amy Wickham 121785021
// Amy Wickham 12178502
// File: ReportDTO.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.dto;

import lombok.Data;
import com.example.meditime.model.Report;

/**
 * Data Transfer Object (DTO) for the Report entity.
 * Used to transfer report data between layers, typically from service to controller
 * or as a response object in APIs.
 */
@Data
public class ReportDTO {

    // Unique identifier for the report
    private Long id;

    // Name of the carer who created or is associated with the report
    private String carerName;

    // Name of the client whom the report concerns
    private String clientName;

    // Notes or observations contained in the report
    private String notes;

    // Date the report was created, stored as a String representation
    private String dateCreated;

    /**
     * Static factory method to convert a Report entity to a ReportDTO.
     * Returns null if the input entity is null.
     *
     * @param report The Report entity to convert
     * @return A ReportDTO with data copied from the entity
     */
    public static ReportDTO fromEntity(Report report) {
        if (report == null) return null;

        ReportDTO dto = new ReportDTO();
        dto.setId(report.getReportId());
        dto.setCarerName(report.getCarer().getName());
        dto.setClientName(report.getClient().getName());
        dto.setNotes(report.getNotes());
        dto.setDateCreated(report.getDateCreated().toString());

        return dto;
    }
}
