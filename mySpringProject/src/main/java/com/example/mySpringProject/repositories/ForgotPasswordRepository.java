package com.example.mySpringProject.repositories;

import com.example.mySpringProject.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {



}
