//Amy Wickham 121785021
package com.example.meditime.service;

import com.example.meditime.dto.ClientMedicationDTO;
import com.example.meditime.dto.ClientWithMedicationsDTO;
import com.example.meditime.model.Client;
import com.example.meditime.model.ClientMedication;
import com.example.meditime.model.Medication;
import com.example.meditime.model.MedicationLog;
import com.example.meditime.repository.ClientMedicationRepository;
import com.example.meditime.repository.ClientRepository;
import com.example.meditime.repository.MedicationLogRepository;
import com.example.meditime.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientMedicationService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private MedicationLogRepository medicationLogRepository;
    @Autowired
    private ClientMedicationRepository clientMedicationRepository;

    public void assignMedication(ClientMedicationDTO dto) {
        Optional<Client> clientOpt = clientRepository.findById(dto.getClientId());
        Optional<Medication> medicationOpt = medicationRepository.findById(dto.getMedicationId());

        if (clientOpt.isPresent() && medicationOpt.isPresent()) {
            ClientMedication cm = new ClientMedication();
            cm.setClient(clientOpt.get());
            cm.setMedication(medicationOpt.get());
            cm.setDosage(dto.getDosage());
            cm.setStartDate(dto.getStartDate());
            cm.setEndDate(dto.getEndDate());
            cm.setPaused(dto.isPaused());
            cm.setScheduledTimes(dto.getScheduledTimes());

            clientMedicationRepository.save(cm);
            System.out.println("Medication schedule assigned successfully.");
        } else {
            System.out.println("Client or Medication not found.");
        }
    }

    public List<String> getMedicationNamesForClient(Long clientId) {
        List<ClientMedication> medications = clientMedicationRepository.findByClient_ClientId(clientId);
        List<String> names = new ArrayList<>();

        for (ClientMedication cm : medications) {
            if (cm.getMedication() != null && cm.getMedication().getName() != null) {
                names.add(cm.getMedication().getName());
            }
        }

        return names;
    }



    /**
     * Retrieves a list of clients assigned to a specific carer, along with each client's medication details.
     *
     * @param carerId The ID of the carer whose clients are to be retrieved.
     * @return A list of ClientWithMedicationsDTO, each containing client info and their medications.
     */
    public List<ClientWithMedicationsDTO> getClientsWithMedications(Long carerId) {
        // Fetch all clients associated with the given carer's user ID
        List<Client> clients = clientRepository.findAllByCarerUserId(carerId);

        // Prepare the result list to hold DTOs containing client and medication data
        List<ClientWithMedicationsDTO> result = new ArrayList<>();

        // Iterate through each client
        for (Client client : clients) {
            // Retrieve medications associated with the current client
            List<ClientMedication> meds = clientMedicationRepository.findByClient_ClientId(client.getClientId());

            // Convert the list of ClientMedication entities to DTOs
            List<ClientMedicationDTO> dtoList = meds.stream()
                    .map(this::convertToDTO)
                    .toList();

            // Create a new DTO object to hold client and their medications
            ClientWithMedicationsDTO clientDTO = new ClientWithMedicationsDTO();
            clientDTO.setClientId(client.getClientId());
            clientDTO.setClientName(client.getName());
            clientDTO.setMedications(dtoList);

            // Add the DTO to the result list
            result.add(clientDTO);
        }
        // Return the final list of clients with their medications
        return result;
    }

    /**
     * Converts a ClientMedication entity to a ClientMedicationDTO.
     *
     * @param cm The ClientMedication entity to convert.
     * @return A DTO representing the medication information.
     */
    private ClientMedicationDTO convertToDTO(ClientMedication cm) {
        ClientMedicationDTO dto = new ClientMedicationDTO();

        // Populate the DTO fields from the entity
        dto.setClientId(cm.getClient().getClientId());
        dto.setClientMedicationId(cm.getClientMedicationId());
        dto.setMedicationId(cm.getMedication().getMedicationId());
        dto.setDosage(cm.getDosage());
        dto.setStartDate(cm.getStartDate());
        dto.setEndDate(cm.getEndDate());
        dto.setPaused(cm.isPaused());
        dto.setScheduledTimes(cm.getScheduledTimes());

        return dto;
    }


//    public List<ClientMedicationDTO> getClientMedicationDTOs(Long clientId) {
//        List<ClientMedication> meds = clientMedicationRepository.findByClient_ClientId(clientId);
//        List<ClientMedicationDTO> dtos = new ArrayList<>();
//
//        for (ClientMedication cm : meds) {
//            ClientMedicationDTO dto = new ClientMedicationDTO();
//            dto.setMedicationName(cm.getMedication().getName());
//            dto.setDosage(cm.getDosage());
//            dto.setFrequency(cm.getFrequency());
//            dto.setStartDate(cm.getStartDate());
//            dto.setEndDate(cm.getEndDate());
//            dtos.add(dto);
//        }
//
//        return dtos;
//    }

    public List<Long> getClientMedicationByUserId(Long clientMedId) {
        List<Long> meds = clientMedicationRepository.findMedicationIdsByClientId(clientMedId);

        return meds;
    }

    public List<ClientMedication> getClientMedicationByClientId(Long clientId) {
        List<ClientMedication> meds = clientMedicationRepository.findByClient_ClientId(clientId);

        return meds;
    }



    public double calculateAdherenceRate(Long clientId) {
    // Get all medication logs for this client's medications
    List<MedicationLog> logs = medicationLogRepository.findByClientMedication_Client_ClientId(clientId);

    if (logs.isEmpty()) {
        return 0.0;
    }

    long totalDoses = logs.size();
    long givenDoses = logs.stream()
            .filter(log -> log.getStatus() == MedicationLog.Status.Given)
            .count();

    return ((double) givenDoses / totalDoses) * 100;
}

}
