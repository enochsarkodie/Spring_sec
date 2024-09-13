package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    private String otp;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    @OneToOne
    private User user;


}
