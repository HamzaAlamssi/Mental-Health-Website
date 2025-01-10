package com.example.MentalHealthSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogOutController {

    @GetMapping(value = "/logout")
    public String login() {
        //  String actionPage = "patient_login";
        return "loginPage";
    }
}
