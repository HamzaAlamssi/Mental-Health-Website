package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}