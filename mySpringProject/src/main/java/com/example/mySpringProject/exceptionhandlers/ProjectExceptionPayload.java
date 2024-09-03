package com.example.mySpringProject.exceptionhandlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProjectExceptionPayload{

    private final String message;
    private final HttpStatus status;
    private final Throwable throwable;


}
