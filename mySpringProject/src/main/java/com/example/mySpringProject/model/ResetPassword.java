package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    private Integer otp;
    private Date expirationDate;
    @OneToOne
    private User user;


}
