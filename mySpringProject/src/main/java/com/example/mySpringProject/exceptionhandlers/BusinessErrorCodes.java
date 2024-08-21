package com.example.mySpringProject.exceptionhandlers;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum BusinessErrorCodes {

    ;
    private  final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description){
        this.code=code;
        this.description=description;
        this.httpStatus=httpStatus;
    }

}
