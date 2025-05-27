// Amy Wickham 12178502
// File: UserDTO.java
// Description: DTO for transferring User data with flat roleId

package com.example.meditime.dto;

import com.example.meditime.model.User;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for the User entity.
 * Used for transferring user data across layers, especially in API responses or requests.
 * This DTO uses a simplified structure with a flat roleId rather than nested Role object.
 */
@Data
public class UserDTO {
    // Unique identifier for the user
    private Long userId;

    // User's full name
    private String name;

    // User's email address
    private String email;

    // Role identifier associated with the user (flattened for simplicity)
    private Long roleId;

    /**
     * Converts a User entity into a UserDTO.
     * Returns null if the input User entity is null.
     *
     * @param user The User entity to convert
     * @return A UserDTO containing relevant user information
     */
    public static UserDTO fromEntity(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoleId(user.getRoleId());
        return dto;
    }

    /**
     * Converts this DTO back to a User entity.
     * Useful when receiving data from external sources to persist as an entity.
     *
     * @return A User entity populated with data from this DTO
     */
    public User toEntity() {
        User user = new User();
        user.setUserId(this.userId);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setRoleId(this.roleId);
        return user;
    }
}
