package com.example.mySpringProject.exceptionhandlers;

import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(LockedException.class)
 public ResponseEntity<ExceptionResponse> handleException(LockedException exp){
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(
                      ExceptionResponse.builder()
                              .errorCodes(ErrorResponse.ACCOUNT_LOCKED.getCode())
                              .errorDescription(ErrorResponse.ACCOUNT_LOCKED.getDescription())
                              .error(exp.getMessage())
                              .build()
              );

 }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCodes(ErrorResponse.ACCOUNT_DISABLED.getCode())
                                .errorDescription(ErrorResponse.ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCodes(ErrorResponse.BAD_CREDENTIALS.getCode())
                                .errorDescription(ErrorResponse.BAD_CREDENTIALS.getDescription())
                                .error(ErrorResponse.BAD_CREDENTIALS.getDescription())
                                .build()
                );

    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp){
       Set<String> errors = new HashSet<>();
       exp.getBindingResult().getAllErrors()
               .forEach( error -> {
                   var errorMessage = error.getDefaultMessage();
                   errors.add(errorMessage);
               });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .errorCodes(ErrorResponse.INTERNAL_SERVER_ERROR.getCode())
                                .errorDescription(ErrorResponse.INTERNAL_SERVER_ERROR.getDescription())
                                .error(exp.getMessage())
                                .build()
                );

    }
}
