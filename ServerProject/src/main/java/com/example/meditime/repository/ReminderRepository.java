// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Reminder entity.
 *
 * Extends JpaRepository to provide CRUD operations for reminders.
 * Includes a method to find all reminders related to a specific client via client ID.
 */
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    /**
     * Retrieves a list of reminders associated with a given client's ID.
     *
     * @param clientId the ID of the client
     * @return list of reminders for the specified client
     */
    List<Reminder> findByClientMedication_Client_ClientId(Long clientId);

    /**
     * Saves the given reminder entity.
     * This method is inherited from JpaRepository and can be overridden if custom behavior is needed.
     *
     * @param reminder the reminder entity to save
     * @return the saved reminder entity
     */
    Reminder save(Reminder reminder);
}
