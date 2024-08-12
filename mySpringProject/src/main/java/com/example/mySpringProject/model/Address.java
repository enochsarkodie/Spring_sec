package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
@AllArgsConstructor
public class Address {

    private String addressLine1;


    private String addressLine2;


    private String city;

    private String country;


}

