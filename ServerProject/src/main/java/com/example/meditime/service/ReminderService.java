//Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.model.Reminder;
import com.example.meditime.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * ReminderService
 *
 * Provides operations related to Reminder entities, including:
 * - Creating new reminders
 * - Retrieving reminders associated with a specific client by their client ID
 *
 * Interacts with the ReminderRepository for persistence.
 */
@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    public Reminder createReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    public List<Reminder> getRemindersByClientId(Long clientId) {
        return reminderRepository.findByClientMedication_Client_ClientId(clientId);
    }
}
