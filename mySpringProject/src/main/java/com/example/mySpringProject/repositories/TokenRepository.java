package com.example.mySpringProject.repositories;

import com.example.mySpringProject.model.TokenModel.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken (String token);
}
