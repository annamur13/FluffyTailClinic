package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.controller.NF.ResourceNotFoundException;
import ru.ssau.towp.fluffytailclinic.dto.AnimalDTO;
import ru.ssau.towp.fluffytailclinic.dto.DiagnosisAppointmentDTO;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisAppointmentRepository;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisRepository;
import ru.ssau.towp.fluffytailclinic.services.DiagnosisAppointmentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diagnosis-appointments")
public class DiagnosisAppointmentController {

    @Autowired
    private DiagnosisAppointmentService diagnosisAppointmentService;
    private DiagnosisAppointmentRepository diagnosisAppointmentRepository;

    // Получить все связи диагнозов и записей
    @GetMapping
    public List<DiagnosisAppointmentDTO> getAllDiagnosisAppointments() {
        return diagnosisAppointmentService.getAllDiagnosisAppointments();
    }

    // Получить связь по ID
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisAppointmentDTO> getDiagnosisAppointmentById(@PathVariable Long id) {
        return diagnosisAppointmentService.getDiagnosisAppointmentById(id);
    }

    // Создать новую связь
    @PostMapping
    public ResponseEntity<DiagnosisAppointment> createDiagnosisAppointment(@RequestBody DiagnosisAppointment diagnosisAppointment) {
        return diagnosisAppointmentService.createDiagnosisAppointment(diagnosisAppointment);
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
        return diagnosisAppointmentService.deleteDiagnosisAppointment(id);
    }
}