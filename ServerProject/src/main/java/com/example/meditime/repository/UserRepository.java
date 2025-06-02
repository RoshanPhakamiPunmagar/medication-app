//Amy Wickham 121785021
package com.example.meditime.repository;

import com.example.meditime.model.Role;
import com.example.meditime.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
/**
 * Repository interface for User entities.
 * Provides methods to find users by role ID and to find a user by email.
 */
public interface UserRepository extends JpaRepository<User, Long> {


     List<User> findByRoleId(Long roleId);


    Optional<User> findByEmail(String email);

}
