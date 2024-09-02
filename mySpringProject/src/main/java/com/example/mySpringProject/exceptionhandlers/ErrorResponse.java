package com.example.mySpringProject.exceptionhandlers;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
public enum ErrorResponse {

    NOT_FOUND(403,FORBIDDEN,"Forbidden"),

    USER_NOT_FOUND(404, NOT_FOUND.getHttpStatus(),"User not found"),

    INCORRECT_CURRENT_PASSWORD(409, CONFLICT,"Current password is incorrect"),

    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "New password does not match"),

    ACCOUNT_LOCKED(302, FORBIDDEN, "User account is locked"),

    ACCOUNT_DISABLED(303, FORBIDDEN, "User account is disabled"),

    EMAIL_ALREADY_EXIST(409, CONFLICT, "Email already exist"),

    BAD_CREDENTIALS(304, FORBIDDEN, "Username or Password is incorrect"),

    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server error")
    ;
    private  final int code;
    private final String description;
    private final HttpStatus httpStatus;

    ErrorResponse(int code, HttpStatus httpStatus, String description){
        this.code=code;
        this.description=description;
        this.httpStatus=httpStatus;
    }

}
