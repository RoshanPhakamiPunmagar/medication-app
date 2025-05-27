// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Role entity.
 *
 * Extends JpaRepository to provide basic CRUD operations for roles.
 * Includes method to find a role by its name.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its role name.
     *
     * @param roleName the name of the role to find
     * @return an Optional containing the found Role, or empty if not found
     */
    Optional<Role> findByRoleName(String roleName);

}
