package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Location;
import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.request.AdminSignUpRequest;
import com.example.MentalHealthSystem.request.DoctorSignUpRequest;
import com.example.MentalHealthSystem.request.PatientSignUpRequest;
import com.example.MentalHealthSystem.service.DoctorService;
import com.example.MentalHealthSystem.service.LocationService;
import com.example.MentalHealthSystem.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("signup")
@Slf4j
public class SignUpController {

    @Autowired
    SignUpService signUpService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    LocationService locationService;
    @GetMapping("/patients")
    public String patientsSignUp(Model model) {
        model.addAttribute("patientSignUpRequest", new PatientSignUpRequest());
        return "signup_patient";
    }

    @PostMapping("/patient_signup")
    public String signUpPatient(@ModelAttribute("patientSignUpRequest") PatientSignUpRequest request) {
        request.setUserRole(UserRoles.PATIENT);
        if (!signUpService.signUp(request)) {
            return "signup_failed";
        }
        return "loginPage";
    }

    @GetMapping("/doctors")
    public String doctorsSignUp(Model model) {
        model.addAttribute("doctorSignUpRequest", new DoctorSignUpRequest());
        return "/JoinUsAsSpecialist";
    }

    @PostMapping("/doctor_signup")
    public String signUpDoctor(@ModelAttribute("doctorSignUpRequest") DoctorSignUpRequest request) {
        request.setUserRole(UserRoles.DOCTOR);

        try {
            MultipartFile cv = request.getCv();
            if (cv != null && !cv.isEmpty()) {
                log.info("CV file name: {}", cv.getOriginalFilename());
                log.info("CV content type: {}", cv.getContentType());
                request.setCvFileName(cv.getOriginalFilename());
                request.setCvContent(cv.getBytes());
                request.setCvContentType(cv.getContentType());
            }

            MultipartFile identityLicense = request.getIdentityLicense();
            if (identityLicense != null && !identityLicense.isEmpty()) {
                log.info("Identity License file name: {}", identityLicense.getOriginalFilename());
                log.info("Identity License content type: {}", identityLicense.getContentType());
                request.setIdentityLicenseFileName(identityLicense.getOriginalFilename());
                request.setIdentityLicenseContent(identityLicense.getBytes());
                request.setIdentityLicenseContentType(identityLicense.getContentType());
            }


        } catch (IOException e) {
            log.error("Error uploading files", e);
            return "error_uploading_files"; // Return a specific error view or message
        }

        if (!signUpService.signUp(request)) {
            return "signup_failed";
        }
        String location = request.getLocation();
        String username = request.getEmail();
        log.error("username {}", username);
        String[] latLng = location.split(", ");
        double latitude = Double.parseDouble(latLng[0]);
        double longitude = Double.parseDouble(latLng[1]);
        Doctor doctor = null;
        if (username != null) {
            doctor = doctorService.getDoctorById(username);
        }
        locationService.saveLocation(latitude, longitude , doctor);

        return "loginPage";
    }

    @GetMapping("/admins")
    public String adminsSignUp(Model model) {
        model.addAttribute("adminSignUpRequest", new AdminSignUpRequest());
        return "signup_admin";
    }

    @PostMapping("/admin_signup")
    public String signUpAdmin(@ModelAttribute("adminSignUpRequest") AdminSignUpRequest request) {
        request.setUserRole(UserRoles.ADMIN);

        if (!signUpService.signUp(request)) {
            return "signup_failed";
        }
        return "loginPage";
    }
    @GetMapping("/Join/Us/As/Specialist")
    public String getJoinUsAsSpecialist(Model model) {
        model.addAttribute("doctorSignUpRequest", new DoctorSignUpRequest());
        log.error("before JoinUsAsSpecialist");

        return "JoinUsAsSpecialist";
    }

    @GetMapping("/Join/Us/As/Patient")
    public String getJoinUsAsPatient(Model model) {
        model.addAttribute("patientSignUpRequest", new PatientSignUpRequest());
        return "signup_patient";
    }
}
