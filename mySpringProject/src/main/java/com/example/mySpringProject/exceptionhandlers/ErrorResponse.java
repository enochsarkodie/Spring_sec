package com.example.mySpringProject.exceptionhandlers;


public enum ErrorResponse {

    EMAIL_ALREADY_EXIST("Email Already Exist");
    public final String label;

    ErrorResponse(String label) {
        this.label = label;
    }

}
