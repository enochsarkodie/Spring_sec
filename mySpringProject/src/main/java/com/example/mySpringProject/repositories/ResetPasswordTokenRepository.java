package com.example.mySpringProject.repositories;

import com.example.mySpringProject.model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer> {
 Optional<ResetPasswordToken> findByToken(String token);

}
