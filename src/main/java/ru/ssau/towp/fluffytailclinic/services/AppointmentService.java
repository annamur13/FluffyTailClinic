package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Получить все записи
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Получить запись по ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // Создать новую запись
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Обновить запись
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setAnimal(appointmentDetails.getAnimal());
        appointment.setVet(appointmentDetails.getVet());
        appointment.setDate(appointmentDetails.getDate());
        appointment.setDescription(appointmentDetails.getDescription());

        return appointmentRepository.save(appointment);
    }

    // Удалить запись
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    // Получить все записи (альтернативное название метода)
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Получить запись по ID (альтернативное название метода)
    public Optional<Appointment> findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }
}