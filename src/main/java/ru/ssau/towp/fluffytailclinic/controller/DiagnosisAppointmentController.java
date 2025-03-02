package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;
import ru.ssau.towp.fluffytailclinic.services.DiagnosisAppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosis-appointments")
public class DiagnosisAppointmentController {

    @Autowired
    private DiagnosisAppointmentService diagnosisAppointmentService;

    // Получить все связи диагнозов и записей
    @GetMapping
    public List<DiagnosisAppointment> getAllDiagnosisAppointments() {
        return diagnosisAppointmentService.getAllDiagnosisAppointments();
    }

    // Получить связь по ID
    @GetMapping("/{id}")
    public DiagnosisAppointment getDiagnosisAppointmentById(@PathVariable Long id) {
        return diagnosisAppointmentService.getDiagnosisAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("DiagnosisAppointment not found"));
    }

    // Создать новую связь
    @PostMapping
    public DiagnosisAppointment createDiagnosisAppointment(@RequestBody DiagnosisAppointment diagnosisAppointment) {
        return diagnosisAppointmentService.createDiagnosisAppointment(diagnosisAppointment);
    }

    // Обновить связь
    @PutMapping("/{id}")
    public DiagnosisAppointment updateDiagnosisAppointment(@PathVariable Long id, @RequestBody DiagnosisAppointment diagnosisAppointmentDetails) {
        return diagnosisAppointmentService.updateDiagnosisAppointment(id, diagnosisAppointmentDetails);
    }

    // Удалить связь
    @DeleteMapping("/{id}")
    public void deleteDiagnosisAppointment(@PathVariable Long id) {
        diagnosisAppointmentService.deleteDiagnosisAppointment(id);
    }
}