package com.example.mySpringProject.controllers;

import com.example.mySpringProject.dtos.RegistrationDTO;
import com.example.mySpringProject.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationDTO registrationDTO) throws MessagingException {
        authenticationService.registerUser(registrationDTO);
        return ResponseEntity.accepted().build();
    }



    }


