package com.example.mySpringProject.exceptionhandlers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;


@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ProjectException.class)
 public ResponseEntity<Object> handleGlobalException(ProjectException projectException) {
     ErrorResponse response = projectException.getResponse();
     var cause = projectException.getCause();
     HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

     switch (response){
         case EMAIL_ALREADY_EXIST -> status = HttpStatus.CONFLICT;
         case
                 INVALID_TOKEN,
                 ACTIVATION_TOKEN_EXPIRED-> status = HttpStatus.UNAUTHORIZED;

         case USER_NOT_FOUND -> status = HttpStatus.NOT_FOUND;
     }

     ProjectExceptionPayload projectExceptionPayload = new ProjectExceptionPayload(
             response.label,
             status,
             cause
     );

    return new ResponseEntity<>(projectExceptionPayload,status);
 }

 @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object>handleExceptions( MethodArgumentNotValidException exp){
     Set<String> errors = new HashSet<>();
     exp.getBindingResult().getAllErrors()
             .forEach(error ->{
                 var errorMessages = error.getDefaultMessage();
                 errors.add(errorMessages);
                     });
     return ResponseEntity
             .status(HttpStatus.BAD_REQUEST)
             .body(errors);
 }
}
