package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.UserLoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginDetailsRepository extends JpaRepository<UserLoginDetails, String> {
    Optional<UserLoginDetails> findByEmail(String email);
}
