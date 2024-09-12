package com.example.mySpringProject.EmailTemplateName;

import lombok.Getter;


@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    RESET_PASSWORD("Reset_Password")

        ;

    private final String name;
        EmailTemplateName(String name){
            this.name = name;
        }

}
