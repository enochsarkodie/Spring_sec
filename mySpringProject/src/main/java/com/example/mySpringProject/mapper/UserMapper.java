package com.example.mySpringProject.mapper;

import com.example.mySpringProject.dtos.UserResponseDTO;
import com.example.mySpringProject.model.User;

public class UserMapper {
    public static UserResponseDTO toDto(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        return dto;

    }

    public static User toEntity(UserResponseDTO dto){
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }
}
