package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.dto.DiagnosisDTO;
import ru.ssau.towp.fluffytailclinic.services.DiagnosisService;

import java.util.List;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    // Получить все диагнозы
    @GetMapping
    public List<DiagnosisDTO> getAllDiagnoses() {
        return diagnosisService.getAllDiagnoses();
    }

    // Получить диагноз по ID
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisDTO> getDiagnosisById(@PathVariable Long id) {
        return diagnosisService.getDiagnosisById(id);
    }

    // Создать новый диагноз
    @PostMapping
    public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) {
        return diagnosisService.createDiagnosis(diagnosis);
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
        return diagnosisService.deleteDiagnosis(id);
    }
}