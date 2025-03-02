package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Получить все записи
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Получить запись по ID
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    // Создать новую запись
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    // Обновить запись
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        return appointmentService.updateAppointment(id, appointmentDetails);
    }

    // Удалить запись
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}