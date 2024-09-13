package com.example.mySpringProject.exceptionhandlers;


public enum ErrorResponse {

    EMAIL_ALREADY_EXIST("Email Already Exist"),
    INVALID_TOKEN("Invalid Token"),
    USER_NOT_FOUND("User not found"),
    FAILED_TO_SEND_EMAIL("Could not send email"),
    ACTIVATION_TOKEN_EXPIRED("Activation token has expired, A new token has been sent to your mail");
    public final String label;

    ErrorResponse(String label) {
        this.label = label;
    }

}
