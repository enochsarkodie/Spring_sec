package com.example.mySpringProject.exceptionhandlers;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ProjectException.class)
 public ResponseEntity<Object> handleGlobalException(ProjectException projectException) {
    return null;
 }
}
