//Amy Wickham 121785021
// Amy Wickham 12178502
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
 * REST controller for managing reminders.
 *
 * Provides endpoints to:
 * - Create a new reminder
 * - Retrieve all reminders for a specific client by client ID
 *
 * All endpoints are prefixed with "/api/reminders".
 */
@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping
    public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
        return ResponseEntity.ok(reminderService.createReminder(reminder));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Reminder>> getRemindersForClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(reminderService.getRemindersByClientId(clientId));
    }
}