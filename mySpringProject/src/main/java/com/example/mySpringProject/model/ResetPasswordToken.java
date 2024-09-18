package com.example.mySpringProject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String verificationCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    public ResetPasswordToken ( User user){
        this.user = user;
    }

    public  ResetPasswordToken(){}
}
