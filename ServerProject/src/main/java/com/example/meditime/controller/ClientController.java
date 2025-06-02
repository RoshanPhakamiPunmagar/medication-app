package com.example.meditime.controller;

import com.example.meditime.dto.ClientDTO;
import com.example.meditime.model.Client;
import com.example.meditime.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * REST endpoint to retrieve a paginated list of all clients.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of clients per page (default is 10)
     * @return a ResponseEntity containing a paginated list of ClientDTOs and metadata
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        // Create a Pageable object based on the requested page and size
        Pageable pageable = PageRequest.of(page, size);

        // Fetch a paginated list of clients from the service layer
        Page<Client> clientPage = clientService.getAllClients(pageable);

        // Convert each Client entity to a ClientDTO to avoid exposing sensitive fields
        List<ClientDTO> clientDTOs = clientPage.getContent().stream()
                .map(client -> new ClientDTO(
                        client.getClientId(),
                        client.getName(),
                        client.getDob().toString(),     // Convert DOB to string for simplicity
                        client.getContactInfo(),
                        client.getCarerUserId()))
                .collect(Collectors.toList());

        // Prepare the response map containing client data and pagination metadata
        Map<String, Object> response = new HashMap<>();
        response.put("clients", clientDTOs);                      // The list of client DTOs
        response.put("currentPage", clientPage.getNumber());      // Current page index
        response.put("totalItems", clientPage.getTotalElements()); // Total number of clients
        response.put("totalPages", clientPage.getTotalPages());    // Total number of pages

        // Return the response with HTTP 200 OK status
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("clientId") Long clientId) {
        Client client = clientService.getClientById(clientId);
        if (client != null) {
            ClientDTO clientDTO = new ClientDTO(
                    client.getClientId(),
                    client.getName(),
                    client.getDob().toString(),
                    client.getContactInfo(),
                    client.getCarerUserId());
            return ResponseEntity.ok(clientDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody ClientDTO dto) {
        Client saved = clientService.addClientFromDTO(dto);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") Long clientId) {
        boolean deleted = clientService.deleteClientById(clientId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(
            @PathVariable("id") Long id,
            @RequestBody ClientDTO clientDTO) {
        Client updatedClient = clientService.updateClientFromDTO(id, clientDTO);
        if (updatedClient != null) {
            return ResponseEntity.ok("Client updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
