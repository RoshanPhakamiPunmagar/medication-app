package com.example.meditime.service;

import com.example.meditime.dto.ClientDTO;
import com.example.meditime.model.Client;
import com.example.meditime.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service class for managing Client entities.
 *
 * Provides CRUD operations including:
 * - Retrieving clients with pagination.
 * - Retrieving a client by ID.
 * - Adding new clients from ClientDTO data.
 * - Updating existing clients from ClientDTO data.
 * - Deleting clients by ID.
 * - Fetching all clients associated with a specific carer user ID.
 *
 * Interacts with the ClientRepository for database operations.
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    //add client using dto
    public Client addClientFromDTO(ClientDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setDob(dto.getDob());
        client.setContactInfo(dto.getContact());
        client.setCarerUserId(dto.getCarerUserId()); // Map carerUserId here
        return clientRepository.save(client);
    }
    //delete client by Id
    public boolean deleteClientById(Long clientId) {
        if (clientRepository.existsById(clientId)) {
            clientRepository.deleteById(clientId);
            return true;
        }
        return false;
    }

    public List<Client> getAllClientsByUserId(Long userId) {
        return clientRepository.findAllByCarerUserId(userId);
    }

    public Client updateClientFromDTO(Long id, ClientDTO dto) {
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            // Update the fields of the client based on the DTO
            client.setName(dto.getName());
            client.setDob(dto.getDob());
            client.setContactInfo(dto.getContact());
            client.setCarerUserId(dto.getCarerUserId()); // Update carerUserId if needed

            return clientRepository.save(client);  // Save and return the updated client
        } else {
            return null;  // If the client is not found
        }
}}
