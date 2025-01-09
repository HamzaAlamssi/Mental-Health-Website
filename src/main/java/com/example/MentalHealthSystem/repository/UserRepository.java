package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.Database.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<Patient> findByEmail(String email);
}
