package com.example.MentalHealthSystem.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.sql.Blob;
import java.util.List;


@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Table(name = "Patient",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Patient extends User{
    public Patient(){ super();}
    private Blob profilePicture;
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
    @Lob
    @Column(name = "profilePictureContent", columnDefinition = "LONGBLOB")
    private byte[] profilePictureContent;
}
