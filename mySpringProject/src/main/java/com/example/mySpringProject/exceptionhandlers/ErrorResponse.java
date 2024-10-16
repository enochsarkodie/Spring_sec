package com.example.mySpringProject.exceptionhandlers;


public enum ErrorResponse {

    EMAIL_ALREADY_EXIST("Email Already Exist"),
    INVALID_TOKEN("Invalid Token"),
    TOKEN_DOES_NOT_EXIST("Token does not exist"),
    USER_NOT_FOUND("User not found"),
    FAILED_TO_SEND_EMAIL("Could not send email"),
    PASSWORDS_DO_NOT_MATCH("New password and confirm password does not match"),
    ACTIVATION_TOKEN_EXPIRED("Activation token has expired, A new token has been sent to your mail");
    public final String label;

    ErrorResponse(String label) {
        this.label = label;
    }

}
