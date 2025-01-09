package com.example.MentalHealthSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @GetMapping("/blogs")
    public String getBlog() {
        return "Blog"; // Assuming BlogName corresponds to a template
    }
    @GetMapping("/{BlogName}")
    public String getBlogPage(@PathVariable("BlogName") String BlogName) {
        return BlogName; // Assuming BlogName corresponds to a template
    }
}
