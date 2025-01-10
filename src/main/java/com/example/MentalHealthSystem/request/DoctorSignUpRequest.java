package com.example.MentalHealthSystem.request;

import com.example.MentalHealthSystem.Database.Appointment;
import com.example.MentalHealthSystem.Database.Location;
import com.example.MentalHealthSystem.constants.Language;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSignUpRequest extends SignUpRequest{
    private String name;

    private String DateOfBirth;

    private String nationality;

    private String phoneNumber;

    private String address;

    private String gender;

    private int age;

    private String country;

    private String city;
    private MultipartFile profilePicture;


    private Set<Language> language;
    int yearsOfExperience;
    private String jobTitle;

    private String cvFileName; // Store file name instead

//    private Blob cvContent; // Store file content as byte array
    private byte[] cvContent;

    private String cvContentType; // Store content type
    private MultipartFile cv;

    private String identityLicenseFileName; // Store file name instead

    private byte[] identityLicenseContent; // Store file content as byte array

    private String identityLicenseContentType; // Store content type
    private MultipartFile identityLicense;

    private int sessionPrice;
    private String aboutTheDoctor;
    List<Appointment> appointments;
    private String location;
}
