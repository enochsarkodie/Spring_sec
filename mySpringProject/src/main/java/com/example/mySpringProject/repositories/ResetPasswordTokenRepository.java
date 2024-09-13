package com.example.mySpringProject.repositories;

import com.example.mySpringProject.model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer> {

}
