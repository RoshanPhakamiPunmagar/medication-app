// Amy Wickham 121785021
// File: ReportController.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.controller;

import com.example.meditime.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for generating reports for managers.
 * This endpoint provides summarized insights or analytics about the system's usage or client medication adherence.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    // Injecting the ReportService that handles the business logic of generating reports
    @Autowired
    private ReportService reportService;

    /**
     * Endpoint to generate a report for a specific manager.
     *
     * @param managerId The ID of the manager requesting the report
     * @return A String containing the generated report (e.g., JSON, plain text, or formatted HTML)
     */
    @GetMapping("/manager/{managerId}")
    public String generateReport(@PathVariable Long managerId) {
        return reportService.generateReportForManager(managerId);
    }
}
