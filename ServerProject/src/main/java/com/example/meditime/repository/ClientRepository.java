//Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {


    List<Client> findByCarerUserId(Long carerUserId);

    @Override
     List<Client> findAll();

    @Override
    Optional<Client> findById(Long id);

    @Override
    Client save(Client client);

    @Override
    void deleteById(Long id);
    
}
