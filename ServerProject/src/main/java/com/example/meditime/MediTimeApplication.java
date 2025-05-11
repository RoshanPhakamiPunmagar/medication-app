package com.example.meditime;

import com.example.meditime.dto.ClientDTO;
import com.example.meditime.dto.ClientMedicationDTO;
import com.example.meditime.model.Client;
import com.example.meditime.model.Role;
import com.example.meditime.repository.RoleRepository;
import com.example.meditime.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MediTimeApplication {

    public static void main(String[] args) {
        // Initialize Spring Boot application
        ApplicationContext context = SpringApplication.run(MediTimeApplication.class, args);

        // Service instances
        ClientService clientService = context.getBean(ClientService.class);
        ClientMedicationService clientMedicationService = context.getBean(ClientMedicationService.class);
        UserService userService = context.getBean(UserService.class);
        MedicationInteractionService medicationInteractionService = context.getBean(MedicationInteractionService.class);
        MedicationService medicationService = context.getBean(MedicationService.class);
        MedicationLogService medicationLogService = context.getBean(MedicationLogService.class);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);

        // Initialize roles on startup
        initializeRoles(roleRepository);

        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        while (true) {
            System.out.println("\n--- MediTime Console ---");
            System.out.println("Select Role:");
            System.out.println("1. Manager");
            System.out.println("2. Carer");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");

            int roleChoice;
            try {
                roleChoice = scanner.nextInt();  // Read user input
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();  // Clear the buffer
                continue;
            }
            scanner.nextLine();  // Clear the buffer

            switch (roleChoice) {
                case 1 -> runManagerConsole(scanner, clientService, clientMedicationService, userService, medicationInteractionService, medicationLogService);
                case 2 -> runCarerConsole(scanner, clientService, clientMedicationService, medicationLogService);
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeRoles(RoleRepository roleRepository) {
        createRoleIfNotExists(roleRepository, "Manager");
        createRoleIfNotExists(roleRepository, "Carer");
    }

    private static void createRoleIfNotExists(RoleRepository roleRepository, String roleName) {
        if (roleRepository.findByRoleName(roleName).isEmpty()) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleRepository.save(role);
            System.out.println("Created role: " + roleName);
        }
    }

    // Manager menu
    private static void runManagerConsole(Scanner scanner,
                                          ClientService clientService,
                                          ClientMedicationService clientMedicationService,
                                          UserService userService,
                                          MedicationInteractionService medicationInteractionService,
                                          MedicationLogService medicationLogService) {

        while (true) {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Add Client");
            System.out.println("2. List Clients");
            System.out.println("3. Update Client");
            System.out.println("4. Delete Client");
            System.out.println("5. Add Carer");
            System.out.println("6. View Carers");
            System.out.println("7. Delete Carer");
            System.out.println("8. Assign Medication Schedule to Client");
            System.out.println("9. Generate Adherence Report");
            System.out.println("10. Assign Carer to Client");
            System.out.println("11. Back to Role Menu");
            System.out.print("Enter option: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();  // Clear the buffer

            switch (choice) {
                case 1 -> addClient(scanner, clientService);
                case 2 -> listClients(clientService);
                case 3 -> updateClient(scanner, clientService);
                case 4 -> deleteClient(scanner, clientService);
                case 5 -> addCarer(scanner, userService);
                case 7 -> deleteCarer(scanner, userService);
                case 8 -> assignMedicationSchedule(scanner, clientMedicationService, medicationInteractionService);
                case 9 -> generateAdherenceReport(scanner, clientMedicationService);
                case 10 -> assignCarerToClient(scanner, clientService);
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void addClient(Scanner scanner, ClientService clientService) {
        System.out.print("Enter client's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter DOB (yyyy-mm-dd): ");
        String dob = scanner.nextLine();
        System.out.print("Enter contact info: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Carer User ID: ");
        Long carerUserId = scanner.nextLong(); scanner.nextLine();

       /// ClientDTO dto = new ClientDTO(name, dob, contact, carerUserId);
       // clientService.addClientFromDTO(dto);
        System.out.println("Client added successfully!");
    }

    private static void listClients(ClientService clientService) {
        clientService.getAllClients().forEach(c ->
                System.out.println("ID: " + c.getClientId() + " | Name: " + c.getName()));
    }

    private static void updateClient(Scanner scanner, ClientService clientService) {
        System.out.print("Enter ID of client to update: ");
        Long id = scanner.nextLong(); scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new DOB (yyyy-mm-dd): ");
        String dob = scanner.nextLine();
        System.out.print("Enter new contact: ");
        String contact = scanner.nextLine();
        System.out.print("Enter new Carer User ID: ");
        Long carerUserId = scanner.nextLong(); scanner.nextLine();

        //Client updated = clientService.updateClientFromDTO(id, new ClientDTO(name, dob, contact, carerUserId));
        //System.out.println(updated != null ? "Client updated." : "Client not found.");
    }

    private static void deleteClient(Scanner scanner, ClientService clientService) {
        System.out.print("Enter ID of client to delete: ");
        Long id = scanner.nextLong(); scanner.nextLine();
        boolean result = clientService.deleteClientById(id);
        System.out.println(result ? "Client deleted." : "Client not found.");
    }

    private static void addCarer(Scanner scanner, UserService userService) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        userService.addUser(name, email, password, "Carer");
        System.out.println("Carer added.");
    }


    private static void deleteCarer(Scanner scanner, UserService userService) {
        System.out.print("Enter carer ID to delete: ");
        Long id = scanner.nextLong(); scanner.nextLine();
        boolean result = userService.deleteUserById(id);
        System.out.println(result ? "Carer deleted." : "Carer not found.");
    }

    private static void assignMedicationSchedule(Scanner scanner, ClientMedicationService clientMedicationService, MedicationInteractionService medicationInteractionService) {
        System.out.print("Client ID: ");
        Long clientId = scanner.nextLong(); scanner.nextLine();
        System.out.print("Medication ID: ");
        Long medicationId = scanner.nextLong(); scanner.nextLine();

        boolean interaction = medicationInteractionService.checkInteractions(clientId, medicationId);
        if (interaction) {
            System.out.println("WARNING: Drug interaction detected!");
        }

        System.out.print("Dosage: ");
        String dosage = scanner.nextLine();
        System.out.print("Start Date (yyyy-mm-dd): ");
        String start = scanner.nextLine();
        System.out.print("End Date (yyyy-mm-dd): ");
        String end = scanner.nextLine();

        ClientMedicationDTO dto = new ClientMedicationDTO();
        dto.setClientId(clientId);
        dto.setMedicationId(medicationId);
        dto.setDosage(dosage);
       // dto.setFrequency(frequency);
        dto.setStartDate(LocalDate.parse(start));
        dto.setEndDate(LocalDate.parse(end));
        clientMedicationService.assignMedication(dto);
        System.out.println("Medication assigned.");
    }

    private static void generateAdherenceReport(Scanner scanner, ClientMedicationService clientMedicationService) {
        System.out.print("Client ID for adherence report: ");
        Long id = scanner.nextLong(); scanner.nextLine();
        double rate = clientMedicationService.calculateAdherenceRate(id);
        System.out.printf("Adherence Rate: %.2f%%\n", rate);
    }

    private static void assignCarerToClient(Scanner scanner, ClientService clientService) {
        System.out.print("Enter Client ID: ");
        Long clientId = scanner.nextLong(); scanner.nextLine();
        System.out.print("Enter Carer ID: ");
        Long carerId = scanner.nextLong(); scanner.nextLine();
        //boolean result = clientService.assignCarerToClient(clientId, carerId)System.out.println(result ? "Carer assigned to client." : "Failed to assign carer.");
    }

    // Carer menu
    private static void runCarerConsole(Scanner scanner,
                                        ClientService clientService,
                                        ClientMedicationService medicationService,
                                        MedicationLogService logService) {

        while (true) {
            System.out.println("\n--- Carer Menu ---");
            System.out.println("1. View Client Medication Schedule");
            System.out.println("2. Mark Medication as Given or Skipped");
            System.out.println("3. Upload Incident Note");
            System.out.println("4. Back to Role Menu");
            System.out.print("Enter option: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();  // Clear the buffer

            switch (choice) {
                case 1 -> viewClientMedicationSchedule(scanner, medicationService);
                case 2 -> markMedicationStatus(scanner, logService);
                case 3 -> uploadIncidentNote(scanner);

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void viewClientMedicationSchedule(Scanner scanner, ClientMedicationService medicationService) {
        System.out.print("Enter client ID: ");
        Long clientId = scanner.nextLong(); scanner.nextLine();

        List<ClientMedicationDTO> meds = medicationService.getClientMedicationDTOs(clientId);
        if (meds.isEmpty()) {
            System.out.println("No medications found for this client.");
        } else {
            meds.forEach(med ->
                    System.out.printf("Medication: %s | Dosage: %s | Frequency: %s | Start: %s | End: %s%n",
                            med.getMedicationName(), med.getDosage(),
                            med.getStartDate(), med.getEndDate()));
        }
    }

    private static void markMedicationStatus(Scanner scanner, MedicationLogService logService) {
        System.out.print("Enter client ID: ");
        Long clientId = scanner.nextLong(); scanner.nextLine();
        System.out.print("Enter medication name to mark: ");
        String medName = scanner.nextLine();
        System.out.print("Was it Given or Skipped? (G/S): ");
        String status = scanner.nextLine().equalsIgnoreCase("G") ? "Given" : "Skipped";
        logService.logMedicationStatus(clientId, medName, status);
        System.out.println("Status saved.");
    }

    private static void uploadIncidentNote(Scanner scanner) {
        System.out.print("Enter client ID: ");
        Long clientId = scanner.nextLong(); scanner.nextLine();
        System.out.print("Enter incident note: ");
        String note = scanner.nextLine();
        // Implement your logic for uploading incident notes
        System.out.println("Incident note uploaded.");
    }
}
