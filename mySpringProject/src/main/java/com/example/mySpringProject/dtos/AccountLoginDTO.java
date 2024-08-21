package com.example.mySpringProject.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountLoginDTO {
   @NotNull
   @NotBlank
    private String email;
   @NotNull
   @NotBlank
    private String password;
}
