package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ssau.towp.fluffytailclinic.controller.NF.ResourceNotFoundException;
import ru.ssau.towp.fluffytailclinic.dto.DiagnosisAppointmentDTO;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.dto.DiagnosisDTO;
import ru.ssau.towp.fluffytailclinic.services.DiagnosisService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    // Получить все диагнозы
    @GetMapping
    public List<DiagnosisDTO> getAllDiagnoses() {
        return diagnosisService.getAllDiagnoses().stream()
                .map(DiagnosisDTO::new)
                .collect(Collectors.toList());
    }

    // Получить диагноз по ID
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisDTO> getDiagnosisById(@PathVariable Long id) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
        return ResponseEntity.ok(new DiagnosisDTO(diagnosis));
    }

    // Создать новый диагноз
    @PostMapping
    public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) {
        Diagnosis savedDiagnosis = diagnosisService.createDiagnosis(diagnosis);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDiagnosis);
    }

    // Обновить диагноз
    @PutMapping("/{id}")
    public ResponseEntity<Diagnosis> updateDiagnosis(@PathVariable Long id, @RequestBody Diagnosis diagnosisDetails) {
        if (!diagnosisService.existsById(id)) { // Проверяем, есть ли диагноз
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diagnosis not found");
        }
        Diagnosis updatedDiagnosis = diagnosisService.updateDiagnosis(id, diagnosisDetails);
        return ResponseEntity.ok(updatedDiagnosis);
    }

    // Удалить диагноз
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return ResponseEntity.noContent().build();
    }
}