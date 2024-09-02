package com.example.mySpringProject.exceptionhandlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse extends Exception {
    private Integer errorCodes;
    private String errorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;

    //treat this as our payload

}
