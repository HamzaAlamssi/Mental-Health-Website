package com.example.MentalHealthSystem.repository;


import com.example.MentalHealthSystem.Database.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByAssessmentName(String assessmentName);
}
