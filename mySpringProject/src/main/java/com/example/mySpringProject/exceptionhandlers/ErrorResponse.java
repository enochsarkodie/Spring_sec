package com.example.mySpringProject.exceptionhandlers;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorResponse {

    NOT_FOUND(404,HttpStatus.FORBIDDEN,"Not found"),
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
