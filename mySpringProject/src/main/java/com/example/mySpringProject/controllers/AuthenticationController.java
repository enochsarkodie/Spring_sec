package com.example.mySpringProject.controllers;

import com.example.mySpringProject.dao.AuthenticationDAO;
import com.example.mySpringProject.dao.ForgotPasswordRequest;
import com.example.mySpringProject.dao.ResetPasswordRequest;
import com.example.mySpringProject.dtos.AccountLoginDTO;
import com.example.mySpringProject.dtos.RegistrationDTO;
import com.example.mySpringProject.exceptionhandlers.ProjectException;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationDAO> registerUser(
            @RequestBody @Valid RegistrationDTO registrationDTO)
            throws ProjectException, MessagingException {
     return  authenticationService.registerUser(registrationDTO);

    }

    @GetMapping("/activate-account")
    public ResponseEntity<AuthenticationDAO> confirm(
            @RequestParam String token
     ) throws MessagingException, ProjectException{
        return ResponseEntity.ok(authenticationService.activateAccount(token).getBody());
     }

    @PostMapping(path= "/login")
    public ResponseEntity<AuthenticationDAO> authenticate(
            @RequestBody @Valid AccountLoginDTO request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<AuthenticationDAO> forgotPassword(
            @RequestBody ForgotPasswordRequest request)throws ProjectException, MessagingException{
        return ResponseEntity.ok(authenticationService.forgotPassword(request));
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<AuthenticationDAO> resetPassword(@RequestBody ResetPasswordRequest request, @RequestParam String token)throws ProjectException{
     return ResponseEntity.ok(authenticationService.resetPassword(token, request));
    }

    @GetMapping( path = "/getAllUsers")
    public List<User> getAllUsers(){
        return authenticationService.getAllUsers();
    }
    }


