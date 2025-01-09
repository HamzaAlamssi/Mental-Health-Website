package com.example.MentalHealthSystem.Database;

import com.example.MentalHealthSystem.constants.DoctorStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Blob;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
public class Doctor extends User{
    public Doctor(){super();}
    @Column(name = "yearsOfExperience")
    int yearsOfExperience;

    @Column(name = "jobTitle")
    private String jobTitle;

    @Column(name = "cvFileName")
    private String cvFileName; // Store file name instead

    @Lob
    @Column(name = "cvContent", length = Integer.MAX_VALUE, columnDefinition = "LONGBLOB")
    private byte[] cvContent;

    @Column(name = "cvContentType")
    private String cvContentType; // Store content type

    @Column(name = "identityLicenseFileName")
    private String identityLicenseFileName; // Store file name instead

    @Lob
    @Column(name = "identityLicenseContent", length = Integer.MAX_VALUE, columnDefinition = "LONGBLOB")
    private byte[] identityLicenseContent;

    @Column(name = "identityLicenseContentType")
    private String identityLicenseContentType; // Store content type

    @Column(name = "sessionPrice")
    private int sessionPrice;

    @Column(name = "aboutTheDoctor")
    private String aboutTheDoctor;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    Set<Appointment> appointments;

    @Column(name = "educations")
    private Set<Education> educations;

    @Column(name = "status")
    private DoctorStatus status;

    @Lob
    @Column(name = "profilePictureContent", columnDefinition = "LONGBLOB")
    private byte[] profilePictureContent;

}
