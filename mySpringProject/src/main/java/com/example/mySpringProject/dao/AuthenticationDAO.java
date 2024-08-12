package com.example.mySpringProject.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationDAO {
    private String status;
    private String message;
    private String token;
}
