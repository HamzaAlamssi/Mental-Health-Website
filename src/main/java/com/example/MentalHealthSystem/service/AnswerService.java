package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AnswerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    ///////////////////////////////////
    // Map ResultSet to Answer object
    public List<Answer> getAnswersByQuestionId(Long questionId) {
        String sql = "SELECT * FROM answer WHERE question_id = ?";
        return jdbcTemplate.query(sql, new Object[]{questionId}, (rs, rowNum) -> {
            Answer answer;
            answer = new Answer();
            return answer;
        });
    }
}
