package com.example.mySpringProject.dao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank
    @NotNull
    private String newPassword;

    @NotBlank
    @NotNull
    private String confirmPassword;
}
