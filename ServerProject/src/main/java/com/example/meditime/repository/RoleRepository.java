//Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for Role entities.
 * Provides method to find a Role by its unique roleName.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);

}
