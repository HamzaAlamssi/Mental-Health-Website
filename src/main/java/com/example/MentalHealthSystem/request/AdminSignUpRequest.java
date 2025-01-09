package com.example.MentalHealthSystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminSignUpRequest  extends SignUpRequest {
    private String name;

    private String dateOfBirth;

    private String nationality;

    private String phoneNumber;

    private String address;

    private String gender;

    private int age;

    private String country;

    private String city;
}
