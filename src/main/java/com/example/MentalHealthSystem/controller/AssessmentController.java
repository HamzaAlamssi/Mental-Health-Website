
package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.service.AssessmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/assessments")
public class AssessmentController {
    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }


    @GetMapping("/questions")
    public String getQuestionsPage(@RequestParam("assessmentName") String assessmentName, Model model) {
        return "questions" + assessmentName;
    }

    @GetMapping("/dashboard")
    public String getAssessmentsPage() {
        return "assessments";
    }


    @PostMapping("/submitTest")
    public String submitTest(
            @RequestParam("assessmentName") String assessmentName,
            @AuthenticationPrincipal UserDetails userDetails,
            HttpServletRequest request,
            Model model
    ){
        log.error("userDetails Name: {}", userDetails.getUsername());
        log.error("Assessment Name: {}", assessmentName);
        String userEmail = userDetails.getUsername();
        if (userEmail == null) {
            return "/homepage";
        }

        List<String> answers = assessmentService.answerOfQuestion(assessmentName, request);
        log.error("SuhibWithSubmit {}", answers.size());
        int numberOfQuestions = assessmentService.getNumberOfQuestions(assessmentName);
        log.error("numberOfQuestions {}", numberOfQuestions);

        assessmentService.saveAssessmentAndAnswers(assessmentName, answers, userDetails, model);
        return "test_results";
    }

    @GetMapping("/viewAssessment")
    public String viewAssessment() {
        return "assessments"; // Assuming the Thymeleaf template is named "patient_reports.html"
    }

}
