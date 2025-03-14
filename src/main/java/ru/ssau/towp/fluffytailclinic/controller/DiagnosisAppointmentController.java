package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<DiagnosisAppointment>> getAllDiagnosisAppointments() {
        List<DiagnosisAppointment> diagnosisAppointments = diagnosisAppointmentService.getAllDiagnosisAppointments();
        return ResponseEntity.ok(diagnosisAppointments);
    }

    // Получить связь по ID
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisAppointment> getDiagnosisAppointmentById(@PathVariable Long id) {
        return diagnosisAppointmentService.getDiagnosisAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Создать новую связь
    @PostMapping
    public ResponseEntity<DiagnosisAppointment> createDiagnosisAppointment(@RequestBody DiagnosisAppointment diagnosisAppointment) {
        if (diagnosisAppointment.getAppointment() == null || diagnosisAppointment.getDiagnosis() == null) {
            return ResponseEntity.badRequest().build();
        }

        DiagnosisAppointment createdDiagnosisAppointment = diagnosisAppointmentService.createDiagnosisAppointment(diagnosisAppointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiagnosisAppointment);
    }

    // Обновить связь
    @PutMapping("/{id}")
    public ResponseEntity<DiagnosisAppointment> updateDiagnosisAppointment(@PathVariable Long id, @RequestBody DiagnosisAppointment diagnosisAppointmentDetails) {
        DiagnosisAppointment updatedDiagnosisAppointment = diagnosisAppointmentService.updateDiagnosisAppointment(id, diagnosisAppointmentDetails);
        return ResponseEntity.ok(updatedDiagnosisAppointment);
    }

    // Удалить связь
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosisAppointment(@PathVariable Long id) {
        diagnosisAppointmentService.deleteDiagnosisAppointment(id);
        return ResponseEntity.noContent().build();
    }
}