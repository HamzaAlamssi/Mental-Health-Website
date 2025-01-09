package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.Database.Admin;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.repository.AdminRepository;
import com.example.MentalHealthSystem.repository.DoctorRepository;
import com.example.MentalHealthSystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/check")
public class CheckEmailController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/patient/check-email")
    public ResponseEntity<?> checkEmailExistencePatient(@RequestParam String email) {
        Optional<Patient> existingUser = patientRepository.findById(email);
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(true); // Email already exists
        } else {
            return ResponseEntity.ok(false); // Email is available
        }
    }

    @GetMapping("/doctor/check-email")
    public ResponseEntity<?> checkEmailExistenceDoctor(@RequestParam String email) {
        Optional<Doctor> existingUser = doctorRepository.findById(email);
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(true); // Email already exists
        } else {
            return ResponseEntity.ok(false); // Email is available
        }
    }
    @GetMapping("/admin/check-email")
    public ResponseEntity<?> checkEmailExistenceAdmin(@RequestParam String email) {
        Optional<Admin> existingUser = adminRepository.findById(email);
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(true); // Email already exists
        } else {
            return ResponseEntity.ok(false); // Email is available
        }
    }
}

