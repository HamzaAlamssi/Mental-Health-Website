package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
