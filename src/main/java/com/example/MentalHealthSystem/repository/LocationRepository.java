package com.example.MentalHealthSystem.repository;


import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByDoctor(Doctor doctor);
}

