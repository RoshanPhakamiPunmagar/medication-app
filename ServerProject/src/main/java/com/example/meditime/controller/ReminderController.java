// Amy Wickham 121785021
// File: ReminderController.java
// Description: See MediTime documentation. This file is part of the medication management system.

package com.example.meditime.controller;

import com.example.meditime.model.Reminder;
import com.example.meditime.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing medication reminders.
 * Handles creation and retrieval of reminders associated with clients.
 */
@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    // Injects the ReminderService that contains the business logic for reminders
    @Autowired
    private ReminderService reminderService;

    /**
     * Endpoint to create a new reminder.
     *
     * @param reminder The reminder object to be saved, received from the request body
     * @return ResponseEntity containing the saved reminder
     */
    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        return ResponseEntity.ok(reminderService.createReminder(reminder));
    }

    /**
     * Endpoint to fetch all reminders for a specific client.
     *
     * @param clientId The ID of the client whose reminders are to be retrieved
     * @return ResponseEntity containing a list of reminders for the specified client
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Reminder>> getRemindersForClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(reminderService.getRemindersByClientId(clientId));
    }
}
