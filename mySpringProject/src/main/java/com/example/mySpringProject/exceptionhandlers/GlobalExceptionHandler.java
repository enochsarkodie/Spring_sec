package com.example.mySpringProject.exceptionhandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
 public ResponseEntity<ExceptionResponse> handleException(LockedException exp){
return null;
 }
}
