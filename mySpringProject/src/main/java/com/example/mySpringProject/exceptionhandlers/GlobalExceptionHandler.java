package com.example.mySpringProject.exceptionhandlers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ProjectException.class)
 public ResponseEntity<Object> handleGlobalException(ProjectException projectException) {
     ErrorResponse response = projectException.getResponse();
     var cause = projectException.getCause();
     HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

     switch (response){
         case EMAIL_ALREADY_EXIST -> status = HttpStatus.CONFLICT;
     }

     ProjectExceptionPayload projectExceptionPayload = new ProjectExceptionPayload(
             response.label,
             status,
             cause
     );

    return new ResponseEntity<>(projectExceptionPayload,status);
 }
}
