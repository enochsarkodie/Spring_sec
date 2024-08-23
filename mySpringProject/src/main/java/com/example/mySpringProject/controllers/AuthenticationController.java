package com.example.mySpringProject.controllers;

import com.example.mySpringProject.dao.AuthenticationDAO;
import com.example.mySpringProject.dtos.AccountLoginDTO;
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
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationDTO registrationDTO) throws MessagingException {
        authenticationService.registerUser(registrationDTO);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDAO> login (@RequestBody @Valid AccountLoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.login(loginDTO));
    }
    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
     ) throws MessagingException{
        authenticationService.activateAccount(token);
     }
    }


