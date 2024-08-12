package com.example.mySpringProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountLoginDTO {
   @NotNull
   @NotBlank
    private String email;
   @NotNull
   @NotBlank
    private String password;
}
