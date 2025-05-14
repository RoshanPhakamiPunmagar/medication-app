// Amy Wickham 12178502
// File: UserDTO.java
// Description: DTO for transferring User data with flat roleId

package com.example.meditime.dto;

import com.example.meditime.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private Long roleId;

    public static UserDTO fromEntity(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoleId(user.getRoleId());
        return dto;
    }

    public User toEntity() {
        User user = new User();
        user.setUserId(this.userId);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setRoleId(this.roleId);
        return user;
    }
}
