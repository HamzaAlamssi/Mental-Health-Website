package com.example.MentalHealthSystem.controller;

import org.springframework.ui.Model;
import com.example.MentalHealthSystem.Database.Appointment;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.Doc;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    DoctorService doctorService;
    @GetMapping(value = "/login")
    public String login1() {
        //  String actionPage = "patient_login";
        return "loginPage";
    }
    @GetMapping(value = "/homepage")
    public String homepage(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "homepage";
    }
    @GetMapping(value = "/")
    public String login() {
        //  String actionPage = "patient_login";
        return "loginPage";
    }

    @GetMapping(value = "/location")
    public String location() {
        //  String actionPage = "patient_login";
        return "location";
    }

    @GetMapping("/login_failed")
    public String authenticationFailed(Model model) {
        return "login_failed";
    }

}
