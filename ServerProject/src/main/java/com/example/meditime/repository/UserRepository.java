// Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Role;
import com.example.meditime.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for User entity.
 *
 * Extends JpaRepository to provide CRUD operations for users.
 * Provides additional methods to find users by role ID and by email.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds all users assigned to a specific role by the role's ID.
     *
     * @param roleId the ID of the role
     * @return list of users with the given role ID
     */
    List<User> findByRoleId(Long roleId);

    /**
     * Finds a user by their email address.
     *
     * @param email the user's email
     * @return an Optional containing the found User, or empty if none found
     */
    Optional<User> findByEmail(String email);
}
