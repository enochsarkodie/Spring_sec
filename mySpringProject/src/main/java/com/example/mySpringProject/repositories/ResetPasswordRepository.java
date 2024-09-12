package com.example.mySpringProject.repositories;

import com.example.mySpringProject.model.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Integer> {



}
