package com.example.mySpringProject.controllers;

import com.example.mySpringProject.dao.AuthenticationDAO;
import com.example.mySpringProject.dtos.AccountLoginDTO;
import com.example.mySpringProject.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class TestController {
    private final AuthenticationService authenticationService;


}
