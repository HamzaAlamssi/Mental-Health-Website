package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Appointment;
import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.Database.Report;
import com.example.MentalHealthSystem.repository.AppointmentRepository;
import com.example.MentalHealthSystem.repository.PatientRepository;
import com.example.MentalHealthSystem.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.type.NullType;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ReportRepository reportRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final JavaMailSender emailSender;
    public PatientService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(id).orElse(null);
    }

    public void updatePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Transactional
    public boolean bookAppointment(Long appointmentId, Patient patient) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && !appointment.isBooked()) {
            appointment.setPatient(patient);
            appointment.setBooked(true);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }
    public List<Appointment> getAppointmentsByPatientEmail(String email) {
        String jpql = "SELECT a FROM Appointment a WHERE a.patient.email = :email";
        TypedQuery<Appointment> query = entityManager.createQuery(jpql, Appointment.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsByDoctorEmailAndBookedIsTrue(String doctorEmail) {
        Query query = entityManager.createQuery(
                "SELECT a FROM Appointment a WHERE a.doctor.email = :doctorEmail AND a.booked = true"
        );
        query.setParameter("doctorEmail", doctorEmail);
        return query.getResultList();
    }

    @Scheduled(fixedRate = 3600000) // Runs every hour
    public void deleteExpiredAppointments() {
        List<Appointment> expiredAppointments = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getSession().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        appointmentRepository.deleteAll(expiredAppointments);
    }

    @Transactional
    public void sendConfirmationEmail(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.isBooked()) {
            Patient patient = appointment.getPatient();
            if (patient != null && patient.getEmail() != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(patient.getEmail());
                message.setSubject("Appointment Confirmation");
                message.setText("Dear " + patient.getName() + ",\n\n" +
                        "Your appointment with Dr. " + appointment.getDoctor().getName() + " on " +
                        appointment.getSession() + " has been confirmed.\n\n" +
                        "We look forward to seeing you.\n\n" +
                        "Best regards,\nYour Clinic");
                emailSender.send(message);
            }
        }
    }

    public void cancelAppointment(Long appointmentId, Patient patient) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.getPatient().getEmail().equals(patient.getEmail())) {
            Doctor doctor = appointment.getDoctor();
            appointment.setBooked(false);
            appointment.setPatient(null);
            appointmentRepository.save(appointment);

            // Send email notification to the doctor
            if (doctor != null && doctor.getEmail() != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(doctor.getEmail());
                message.setSubject("Appointment Cancellation Notification");
                message.setText("Dear Dr. " + doctor.getName() + ",\n\n" +
                        "Your appointment with patient " + patient.getName() + " has been cancelled.\n\n" +
                        "Best regards,\nYour Clinic");
                emailSender.send(message);
            }
        }
    }
    public void addReport(Long appointmentId, String reportContent) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.isBooked()) {
            Report report = new Report(reportContent, appointment);
            reportRepository.save(report);

            Patient patient = appointment.getPatient();
            if (patient != null && patient.getEmail() != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(patient.getEmail());
                message.setSubject("New Report from Dr. " + appointment.getDoctor().getName());
                message.setText("Dear " + patient.getName() + ",\n\n" +
                        "Dr. " + appointment.getDoctor().getName() + " has written a new report for you. " +
                        "Please log in to your account to view the report.\n\n" +
                        "Best regards,\n"+
                        "Your Clinic");
                emailSender.send(message);
            }
        }
    }
    public List<Report> getReportsForPatient(String patientId) {
        String sql = "SELECT * FROM report WHERE appointment_id IN " +
                "(SELECT id FROM appointment WHERE patient_id = ?)";
        return jdbcTemplate.query(sql, new Object[]{patientId}, (rs, rowNum) -> {
            Report report = new Report();
            report.setId(rs.getLong("id"));
            report.setContent(rs.getString("content"));
            // Assuming appointment_id is a foreign key in the report table referencing the Appointment table
            Long appointmentId = rs.getLong("appointment_id");
            // Assuming you have a method to get appointments by their ID
            Appointment appointment = appointmentRepository.getReferenceById(appointmentId); // Replace this with your appropriate method
            report.setAppointment(appointment);
            return report;
        });
    }
}
