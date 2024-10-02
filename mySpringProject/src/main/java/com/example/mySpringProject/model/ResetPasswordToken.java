package com.example.mySpringProject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

 public ResetPasswordToken (){}

}
