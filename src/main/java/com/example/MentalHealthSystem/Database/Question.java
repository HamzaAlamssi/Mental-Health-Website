package com.example.MentalHealthSystem.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "content")
    String content;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    Assessment assessment;

}
