package com.example.meditime;

import com.example.meditime.model.Role;
import com.example.meditime.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
/**
 * MediTimeApplication
 *
 * Main Spring Boot application class.
 *
 * Responsibilities:
 * - Starts the Spring Boot application.
 * - Initializes essential roles ("Manager" and "Carer") in the database at startup
 *   if they do not already exist.
 */
@SpringBootApplication
public class MediTimeApplication {

    public static void main(String[] args) {
        // Start Spring Boot app
        ApplicationContext context = SpringApplication.run(MediTimeApplication.class, args);

        // Initialize roles once on startup
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        initializeRoles(roleRepository);
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
}
