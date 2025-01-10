package com.example.MentalHealthSystem.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Education implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "university")
    String university;

    @Column(name = "specialization")
    String specialization;

    @Column(name = "degree")
    String degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; // Foreign key for Doctor

}
