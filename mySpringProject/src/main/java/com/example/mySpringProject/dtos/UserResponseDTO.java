package com.example.mySpringProject.dtos;

import com.example.mySpringProject.model.User;
import com.example.mySpringProject.model.role.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
