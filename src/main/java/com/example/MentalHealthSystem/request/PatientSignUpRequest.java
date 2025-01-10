package com.example.MentalHealthSystem.request;

import com.example.MentalHealthSystem.constants.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientSignUpRequest extends SignUpRequest {

    private String name;

    private String dateOfBirth;

    private String nationality;

    private String phoneNumber;

    private String address;

    private String gender;

    private int age;

    private String country;

    private String city;

    private MultipartFile profilePicture;

}
