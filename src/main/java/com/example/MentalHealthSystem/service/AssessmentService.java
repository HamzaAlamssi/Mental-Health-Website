package com.example.MentalHealthSystem.service;


import com.example.MentalHealthSystem.Database.*;
import com.example.MentalHealthSystem.constants.AssessmentInfo;
import com.example.MentalHealthSystem.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@Service
public class AssessmentService {


    private final AssessmentRepository assessmentRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository, AnswerRepository answerRepository, QuestionRepository questionRepository, PatientRepository patientRepository) {
        this.assessmentRepository = assessmentRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.patientRepository = patientRepository;
    }

    public List<String> answerOfQuestion(String assessmentName, HttpServletRequest request) {
        List<String> answers = new ArrayList<>();
        int numberOfQuestions = getNumberOfQuestions(assessmentName);
        for (int i = 1; i <= numberOfQuestions; i++) {
            String answer = request.getParameter("answer" + i);
            if (answer != null) {
                answers.add(answer);
            }
        }
        return answers;
    }
    public int getNumberOfQuestions(String assessmentName) {
        return AssessmentInfo.getNumberOfQuestions(assessmentName);
    }
    public int calculateScore(List<String> answers) {
        int totalScore = 0;
        for (String answer : answers) {
            totalScore += Integer.parseInt(answer);
        }
        return totalScore;
    }

    public String getCategory(int totalScore, String symptomName, int numberOfQuestions) {
        int maxScore = numberOfQuestions * 3;
        double proportion = (double) totalScore / maxScore;
        if (proportion >= 0.0 && proportion < 0.25) {
            return "Minimal " + symptomName + " symptoms";
        } else if (proportion >= 0.25 && proportion < 0.5) {
            return "Mild " + symptomName + " symptoms";
        } else if (proportion >= 0.5 && proportion < 0.75) {
            return "Moderate " + symptomName + " symptoms";
        } else if (proportion >= 0.75 && proportion < 1.0) {
            return "Moderately severe " + symptomName + " symptoms";
        } else if (proportion >= 1.0) {
            return "Severe " + symptomName + " symptoms";
        } else {
            throw new IllegalArgumentException("Invalid total score: " + totalScore);
        }
    }


    public void saveAssessmentAndAnswers(String assessmentName, List<String> answers,
                                         @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<Patient> optionalPatient = patientRepository.findById(userDetails.getUsername());
        if (optionalPatient.isPresent()) {
            int totalScore = calculateScore(answers);

            String category = getCategory(totalScore, assessmentName,
                    AssessmentInfo.getNumberOfQuestions(assessmentName));

            model.addAttribute("totalScore", totalScore);
            model.addAttribute("category", category);
            Patient patient = optionalPatient.get();

            // Create a new assessment
            Assessment assessment = new Assessment();

            assessment.setName(assessmentName);
            assessment.setUser(patient);
            assessment.setCategory(category);
            assessment.setScore(totalScore);
            assessmentRepository.save(assessment);

            Set<Question> assessmentQuestions = getQuestions(assessmentName, answers, patient, assessment);

//            questionRepository.saveAll(assessmentQuestions);
        } else {
            // Handle case where patient is not found
            throw new IllegalArgumentException("User not found with username: " + userDetails.getUsername());
        }
    }

    public Set<Question> getQuestions(String assessmentName, List<String> answers, Patient patient, Assessment assessment) {
        Set<Question> assessmentQuestions = new HashSet<>();
        Set<String> questionsContent = AssessmentInfo.getAssessmentQuestions(assessmentName);

        AtomicInteger index = new AtomicInteger(0);

        questionsContent.forEach(question -> {
            Question objQuestion = new Question();
            Answer answer = new Answer();
            objQuestion.setContent(question);
            objQuestion.setAssessment(assessment);
            questionRepository.save(objQuestion);
            answer.setAssessment(assessment);
//            objQuestion.setAnswer(answer);

            answer.setContent(answers.get(index.getAndIncrement()));
            answer.setQuestion(objQuestion);


            answer.setQuestion(objQuestion);
            answerRepository.save(answer);
            assessmentQuestions.add(objQuestion);
        });
        return assessmentQuestions;
    }





}
