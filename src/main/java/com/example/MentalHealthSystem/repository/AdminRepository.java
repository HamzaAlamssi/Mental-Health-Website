package com.example.MentalHealthSystem.repository;

import com.example.MentalHealthSystem.Database.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,String> {
  //  Optional<Admin> findByEmail(String email);
}
