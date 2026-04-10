// com.example.appenquetes1.mapper.UserMapper.java
package com.example.appenquetes1.mapper;

import com.example.appenquetes1.dto.user.UserResponseDTO;
import com.example.appenquetes1.entity.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        if (user == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setIsActive(user.getIsActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setLastLogin(user.getLastLogin());

        return dto;
    }
}