package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.*;
import com.example.MentalHealthSystem.constants.DoctorStatus;
import com.example.MentalHealthSystem.constants.Language;
import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.repository.*;
import com.example.MentalHealthSystem.request.AdminSignUpRequest;
import com.example.MentalHealthSystem.request.DoctorSignUpRequest;
import com.example.MentalHealthSystem.request.PatientSignUpRequest;
import com.example.MentalHealthSystem.request.SignUpRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class SignUpService {


    @Autowired
    private UserLoginDetailsRepository userLoginDetailsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private  AdminRepository adminRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean signUp(SignUpRequest request) {
        try {
            saveUserLoginDetails(request);

            if (UserRoles.PATIENT.equals(request.getUserRole())){
                savePatientDetails((PatientSignUpRequest)request);
            } else if (UserRoles.ADMIN.equals(request.getUserRole())) {
                saveAdminDetails((AdminSignUpRequest)request);
            } else {
                saveDoctorDetails((DoctorSignUpRequest)request);
            }
            return true;
        } catch (Exception e){
            log.error("exception ",e);
            return false;
        }

    }

    private void savePatientDetails(PatientSignUpRequest request) {
        log.error("hi there");
        Patient patient = Patient.builder()
                .age(request.getAge())
                .gender(request.getGender())
                .name(request.getName())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .city(request.getCity())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .assessments(new HashSet<>())
                .country(request.getCountry())
                .build();
        patientRepository.save(patient);
    }

    private void saveAdminDetails(AdminSignUpRequest request) {
        log.error("hi there");
        Admin admin = Admin.builder()
                .age(request.getAge())
                .gender(request.getGender())
                .name(request.getName())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .city(request.getCity())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .assessments(new HashSet<>())
                .country(request.getCountry())
                .build();
        adminRepository.save(admin);
    }

    private void saveDoctorDetails(DoctorSignUpRequest request) {
        Doctor doctor = Doctor.builder()
                .gender(request.getGender())
                .name(request.getName())
                .status(DoctorStatus.PENDING)
                .address(request.getAddress())
                .nationality(request.getNationality())
                .city(request.getCity())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .assessments(new HashSet<>())
                .country(request.getCountry())
                .aboutTheDoctor(request.getAboutTheDoctor())
                .jobTitle(request.getJobTitle())
                .yearsOfExperience(request.getYearsOfExperience())
                .sessionPrice(request.getSessionPrice())
                .appointments(new HashSet<>())
                .age(request.getAge())
                .cvFileName(request.getCvFileName())
                .cvContent(request.getCvContent())
                .cvContentType(request.getCvContentType())
                .identityLicenseFileName(request.getIdentityLicenseFileName())
                .identityLicenseContent(request.getIdentityLicenseContent())
                .identityLicenseContentType(request.getIdentityLicenseContentType())
                .build();
        doctorRepository.save(doctor);
    }


    private void saveUserLoginDetails (SignUpRequest request){
        UserLoginDetails userLoginDetails = new UserLoginDetails();
        userLoginDetails.setEmail(request.getEmail());
        userLoginDetails.setPassword(passwordEncoder.encode(request.getPassword()));
        userLoginDetails.setRole(request.getUserRole());
        userLoginDetailsRepository.save(userLoginDetails);
    }


}
