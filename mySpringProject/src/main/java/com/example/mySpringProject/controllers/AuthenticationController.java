package com.example.mySpringProject.controllers;

import com.example.mySpringProject.dao.AuthenticationDAO;
import com.example.mySpringProject.dtos.AccountLoginDTO;
import com.example.mySpringProject.dtos.RegistrationDTO;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid RegistrationDTO registrationDTO)
            throws MessagingException {
        authenticationService.registerUser(registrationDTO);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
     ) throws MessagingException{
        authenticationService.activateAccount(token);
     }

    @PostMapping(path= "/login")
    public ResponseEntity<AuthenticationDAO> authenticate(
            @RequestBody @Valid AccountLoginDTO request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

     @GetMapping(path = "/users")
    public List<User> getAllUsers(
     ){
        return (authenticationService.getAllUsers());
     }


    }


