//Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for managing Client entities.
 * Provides methods to retrieve clients associated with a specific carer,
 * as well as standard CRUD operations.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {


    List<Client> findAllByCarerUserId(Long carerUserId);

    @Override
     List<Client> findAll();

    @Override
    Optional<Client> findById(Long id);

    @Override
    Client save(Client client);

    @Override
    void deleteById(Long id);
    
}
