package com.example.MentalHealthSystem.controller;

import com.example.MentalHealthSystem.Database.Appointment;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.Database.Report;
import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.request.DoctorSignUpRequest;
import com.example.MentalHealthSystem.request.SignUpRequest;
import com.example.MentalHealthSystem.service.DoctorService;
import com.example.MentalHealthSystem.service.PatientService;
import com.example.MentalHealthSystem.service.SignUpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
@Log4j2
@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    DoctorService doctorService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/dashboard")
    public String doctorDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Check if the user is logged in
        String username = userDetails.getUsername();
        if (username != null) {
            Doctor doctor = doctorService.getDoctorById(username);
            List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctor.getEmail());
            List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(username);
            model.addAttribute("bookedAppointments", bookedAppointments);
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointments", appointments);
            return "doctor_dashboard"; // Redirect to the doctor dashboard page
        } else {
            return "loginPage"; // Redirect to login if not logged in
        }
    }

    @PostMapping("/page/{email}")
    public String getDoctorProfile(@PathVariable String email, Model model) {
        Doctor doctor = doctorService.getDoctorById(email);
        model.addAttribute("doctor", doctor);
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(email);
        model.addAttribute("appointments", appointments);
        return "doctor_dashboard";
    }

    @PostMapping("/{email}/update")
    public String updateDoctorProfile(@PathVariable String email, @ModelAttribute DoctorSignUpRequest doctorSUR,
                                      HttpServletRequest request, Model model) {
        try {
            // Get the doctor by ID
            Doctor doctor = doctorService.getDoctorById(email);

            // Update the doctor's profile with the data from the form
            doctor.setName(doctorSUR.getName());
            doctor.setYearsOfExperience(doctorSUR.getYearsOfExperience());
            doctor.setCountry(doctorSUR.getCountry());
            doctor.setCity(doctorSUR.getCity());
            doctor.setJobTitle(doctorSUR.getJobTitle());
            doctor.setNationality(doctorSUR.getNationality());
            doctor.setPhoneNumber(doctorSUR.getPhoneNumber());
            doctor.setAddress(doctorSUR.getAddress());
            doctor.setGender(doctorSUR.getGender());
            doctor.setAge(doctorSUR.getAge());
            doctor.setSessionPrice(doctorSUR.getSessionPrice());
            doctor.setAboutTheDoctor(doctorSUR.getAboutTheDoctor());

            // Handle CV upload
            MultipartFile cv = doctorSUR.getCv();
            if (cv != null && !cv.isEmpty()) {
                doctor.setCvFileName(cv.getOriginalFilename());
                doctor.setCvContent(cv.getBytes());
                doctor.setCvContentType(cv.getContentType());
            }

            // Handle Profile Picture upload
            MultipartFile profilePicture = doctorSUR.getProfilePicture();
            if (profilePicture != null && !profilePicture.isEmpty()) {
//                doctor.setProfilePictureFileName(profilePicture.getOriginalFilename());
                log.error("profilePicture.getOriginalFilename");
                doctor.setProfilePictureContent(profilePicture.getBytes());
                log.error("profilePicture.getBytes()");
//                doctor.setProfilePictureContentType(profilePicture.getContentType());
                log.error("profilePicture.getContentType()");
            }

            // Handle Identity License upload
            MultipartFile identityLicense = doctorSUR.getIdentityLicense();
            if (identityLicense != null && !identityLicense.isEmpty()) {
                doctor.setIdentityLicenseFileName(identityLicense.getOriginalFilename());
                doctor.setIdentityLicenseContent(identityLicense.getBytes());
                doctor.setIdentityLicenseContentType(identityLicense.getContentType());
            }

            // Update the doctor in the database
            doctorService.updateDoctor(doctor);

            // Redirect back to the doctor's profile page

            List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctor.getEmail());
            List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(email);
            model.addAttribute("bookedAppointments", bookedAppointments);
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointments", appointments);
            return "doctor_dashboard"; // Redirect to the doctor dashboard page
        } catch (Exception e) {
            // Log the error and handle appropriately
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/{email}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String email) {
        Doctor doctor = doctorService.getDoctorById(email);

        if (doctor == null || doctor.getProfilePictureContent() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Assuming it's JPEG, change if necessary

        return new ResponseEntity<>(doctor.getProfilePictureContent(), headers, HttpStatus.OK);
    }





    @PostMapping("/{email}/appointments/add")
    public String addAppointment(@PathVariable String email,
                                 @RequestParam("appointmentDateTime") LocalDateTime appointmentDateTime, Model model) {

        doctorService.addAppointment(email, appointmentDateTime);
        Doctor doctor = doctorService.getDoctorById(email);
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctor.getEmail());
        List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(email);
        model.addAttribute("bookedAppointments", bookedAppointments);
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "doctor_dashboard"; // Redirect to the doctor dashboard page
    }
    @PostMapping("/{email}/appointments/{appointmentId}/update")
    public String updateAppointment(@PathVariable String email,
                                    @PathVariable Long appointmentId,
                                    @RequestParam("session") LocalDateTime session,Model model) {

        doctorService.updateAppointment(email, appointmentId, session);
        Doctor doctor = doctorService.getDoctorById(email);
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctor.getEmail());
        List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(email);
        model.addAttribute("bookedAppointments", bookedAppointments);
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "doctor_dashboard"; // Redirect to the doctor dashboard page
    }

    @PostMapping("/{email}/appointments/{appointmentId}/delete")
    public String deleteAppointment(@PathVariable String email,
                                    @PathVariable Long appointmentId, Model model) {

        doctorService.deleteAppointment(email, appointmentId);
        Doctor doctor = doctorService.getDoctorById(email);
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctor.getEmail());
        List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(email);
        model.addAttribute("bookedAppointments", bookedAppointments);
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "doctor_dashboard"; // Redirect to the doctor dashboard page
    }

    @GetMapping("/{email}/cv")
    public ResponseEntity<InputStreamResource> getCV(@PathVariable String email) {
        Doctor doctor = doctorService.findByEmail(email);
        if (doctor == null || doctor.getCvContent() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] cvContent = doctor.getCvContent();
        String cvFileName = doctor.getCvFileName();
        String cvContentType = doctor.getCvContentType();

        ByteArrayInputStream bis = new ByteArrayInputStream(cvContent);
        InputStreamResource resource = new InputStreamResource(bis);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + cvFileName + "\"")
                .contentType(MediaType.parseMediaType(cvContentType))
                .body(resource);
    }

    @GetMapping("/{email}/identityLicense")
    public ResponseEntity<InputStreamResource> getIdentityLicense(@PathVariable String email) throws SQLException {
        Doctor doctor = doctorService.findByEmail(email);
        byte[] identityLicenseContent = doctor.getIdentityLicenseContent();
        String identityLicenseFileName = doctor.getIdentityLicenseFileName();
        String identityLicenseContentType = doctor.getIdentityLicenseContentType();

        ByteArrayInputStream bis = new ByteArrayInputStream(identityLicenseContent);
        InputStreamResource resource = new InputStreamResource(bis);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + identityLicenseFileName + "\"")
                .contentType(MediaType.parseMediaType(identityLicenseContentType))
                .body(resource);
    }


    @PostMapping("/{appointmentId}/sendConfirmation")
    public String sendConfirmationEmail(@PathVariable Long appointmentId,@AuthenticationPrincipal UserDetails userDetails,Model model) {
        patientService.sendConfirmationEmail(appointmentId);
        String userEmail = userDetails.getUsername();
        Doctor doctor = doctorService.findByEmail(userEmail);
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(userEmail);
        List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(userEmail);
        model.addAttribute("bookedAppointments", bookedAppointments);
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "doctor_dashboard"; // Redirect to the doctor dashboard page
    }
    @PostMapping("/{appointmentId}/addReport")
    public String addReport(@PathVariable Long appointmentId, @RequestParam("report") String report,@AuthenticationPrincipal UserDetails userDetails,Model model) {
        patientService.addReport(appointmentId, report);
        String userEmail = userDetails.getUsername();
        Doctor doctor = doctorService.findByEmail(userEmail);
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(doctor.getEmail());
        List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(userEmail);
        model.addAttribute("bookedAppointments", bookedAppointments);
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "doctor_dashboard"; // Redirect to the doctor dashboard page
    }
    @GetMapping("/availableAppointments")
    public String getavailableAppointments(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        String username = userDetails.getUsername();
        if (username != null) {
            List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(username);
            model.addAttribute("appointments", appointments);
            return "availableAppointments";
        } else {
            return "loginPage";
        }
    }

    @GetMapping("/GetAddAppointments")
    public String GetAddAppointments(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        String username = userDetails.getUsername();
        if (username != null) {
            List<Appointment> bookedAppointments = patientService.getAppointmentsByDoctorEmailAndBookedIsTrue(username);
            model.addAttribute("bookedAppointments", bookedAppointments);
            return "ddAppointments";
        } else {
            return "loginPage";
        }
    }

    @GetMapping("/EditProfile")
    public String EditProfile(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        log.error("enter EditProfile");
        String username = userDetails.getUsername();
        if (username != null) {
            Doctor doctor = doctorService.getDoctorById(username);
            model.addAttribute("doctor", doctor);
            log.error("after EditProfile");
            return "EditDoctorProfile";
        } else {
            return "loginPage";
        }
    }

}
