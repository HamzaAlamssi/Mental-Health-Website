package com.example.MentalHealthSystem.repository;


import com.example.MentalHealthSystem.Database.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
