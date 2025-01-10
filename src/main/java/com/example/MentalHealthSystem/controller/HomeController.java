package com.example.MentalHealthSystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping(value = "/patient/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.error(userDetails.getUsername());

        model.addAttribute("username", userDetails.getUsername());
        return "home";
    }
    @GetMapping(value = "/doctor/home")
    public String doctorLogin() {
        String actionPage = "doctor_home";
        return "doctor_home";
    }
    @GetMapping(value = "/home")
    public String login() {
        String actionPage = "patient_home";
        return "home";
    }
}
