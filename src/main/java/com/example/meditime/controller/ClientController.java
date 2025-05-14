package com.example.meditime.controller;

import com.example.meditime.dto.ClientDTO;
import com.example.meditime.model.Client;
import com.example.meditime.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientDTO> getAllClients() {
        // Assuming clientService returns a list of Client entities
        return clientService.getAllClients().stream()
                .map(client -> new ClientDTO(
                        client.getClientId(),
                        client.getName(),
                        client.getDob().toString(),
                        client.getContactInfo(),
                        client.getCarerUserId()))
                .collect(Collectors.toList());
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