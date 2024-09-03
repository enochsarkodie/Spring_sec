package com.example.mySpringProject.exceptionhandlers;

import lombok.Data;

@Data
public class ProjectException extends Exception{
    private final ErrorResponse response;

    public ProjectException( ErrorResponse response) {
        super( response.toString().replace("_ ", " "));
        this.response = response;
    }
}
