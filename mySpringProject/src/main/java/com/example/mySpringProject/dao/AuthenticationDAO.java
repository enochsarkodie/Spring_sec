package com.example.mySpringProject.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationDAO  {
    private String status;
    private String message;
    private String token;
}
