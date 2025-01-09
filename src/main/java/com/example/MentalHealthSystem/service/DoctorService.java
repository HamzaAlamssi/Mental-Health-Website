package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Appointment;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.repository.AppointmentRepository;
import com.example.MentalHealthSystem.repository.DoctorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final JavaMailSender emailSender;

    public DoctorService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }




    public void updateAppointment(String doctorId, Long appointmentId,
                                  LocalDateTime session) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.getDoctor().getEmail().equals(doctorId)) {
            boolean wasBooked = appointment.isBooked();
            appointment.setSession(session);
            appointmentRepository.save(appointment);

            // Send email notification if the appointment was booked
            if (wasBooked) {
                Patient patient = appointment.getPatient();
                if (patient != null && patient.getEmail() != null) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(patient.getEmail());
                    message.setSubject("Appointment Update Notification");
                    message.setText("Dear " + patient.getName() + ",\n\n" +
                            "Your appointment with Dr. " + appointment.getDoctor().getName() + " has been updated.\n" +
                            "New Appointment Date and Time: " + appointment.getSession() + "\n\n" +
                            "We apologize for any inconvenience.\n\n" +
                            "Best regards,\nYour Clinic");
                    emailSender.send(message);
                }
            }
        }
    }

    public void deleteAppointment(String doctorId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.getDoctor().getEmail().equals(doctorId)) {
            if (appointment.isBooked()) {
                Patient patient = appointment.getPatient();
                if (patient != null && patient.getEmail() != null) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(patient.getEmail());
                    message.setSubject("Appointment Cancellation Notice");
                    message.setText("Dear " + patient.getName() + ",\n\n" +
                            "We regret to inform you that your appointment with Dr. " +
                            appointment.getDoctor().getName() + " on " +
                            appointment.getSession() + " has been cancelled.\n\n" +
                            "We apologize for any inconvenience caused.\n\n" +
                            "Best regards,\nYour Clinic");
                    emailSender.send(message);
                }
            }
            appointmentRepository.delete(appointment);
        }
    }

    public void addAppointment(String doctorId, LocalDateTime appointmentDateTime) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setSession(appointmentDateTime);
            appointment.setBooked(false);
            appointmentRepository.save(appointment);
        }
    }

    public List<Doctor> getAllDoctors()
    {
        return doctorRepository.findAll();
    }
    public Doctor findByEmail(String email)
    {
        return doctorRepository.findById(email).orElse(null);
    }
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }
    public List<Appointment> getAppointmentsByDoctorId(String doctorId, boolean booked) {
        String sql = "SELECT * FROM appointment WHERE doctor_id = ? AND booked = ?";
        return jdbcTemplate.query(sql, new Object[]{doctorId, booked}, this::mapRowToAppointment);
    }
    private Appointment mapRowToAppointment(ResultSet rs, int rowNum) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getLong("id"));
        appointment.setSession(rs.getObject("session", LocalDateTime.class));
        // Set other fields as necessary
        return appointment;
    }

    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        String sql = "SELECT * FROM appointment WHERE doctor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{doctorId}, (rs, rowNum) -> {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getLong("id"));

            // Create a Doctor object and set its ID
            Doctor doctor = new Doctor();
            doctor.setEmail(doctorId);
            appointment.setDoctor(doctor);

            appointment.setSession(rs.getTimestamp("session").toLocalDateTime());
            // Map other columns as needed
            return appointment;
        });
    }
}
