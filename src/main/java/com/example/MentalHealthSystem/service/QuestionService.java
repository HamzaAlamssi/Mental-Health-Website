package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Question> getQuestionsByAssessmentId(Long assessmentId) {
        String sql = "SELECT * FROM question WHERE assessment_id = ?";
        return jdbcTemplate.query(sql, new Object[]{assessmentId}, (rs, rowNum) -> {
            Question question = new Question();
            question.setContent(rs.getString("content"));
            return question;
        });

    }
}
