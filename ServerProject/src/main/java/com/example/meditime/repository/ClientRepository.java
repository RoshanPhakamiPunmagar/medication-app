// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Client entities.
 *
 * Extends JpaRepository to provide CRUD operations and custom queries
 * for Client objects in the MediTime medication management system.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Finds all clients assigned to a specific carer by the carer's user ID.
     *
     * @param carerUserId the ID of the carer user
     * @return a list of clients associated with the given carer
     */
    List<Client> findAllByCarerUserId(Long carerUserId);

    /**
     * Retrieves all clients from the database.
     * Overrides JpaRepository's findAll method.
     *
     * @return list of all clients
     */
    @Override
    List<Client> findAll();

    /**
     * Finds a client by its unique identifier.
     * Overrides JpaRepository's findById method.
     *
     * @param id the client ID
     * @return an Optional containing the client if found, or empty otherwise
     */
    @Override
    Optional<Client> findById(Long id);

    /**
     * Saves a client entity to the database.
     * Overrides JpaRepository's save method.
     *
     * @param client the client to save
     * @return the saved client entity
     */
    @Override
    Client save(Client client);

    /**
     * Deletes a client from the database by its ID.
     * Overrides JpaRepository's deleteById method.
     *
     * @param id the ID of the client to delete
     */
    @Override
    void deleteById(Long id);

}
