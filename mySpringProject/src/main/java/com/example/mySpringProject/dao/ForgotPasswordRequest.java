package com.example.mySpringProject.dao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @Email
    @NotBlank
    @NotNull
    private String email;
}
