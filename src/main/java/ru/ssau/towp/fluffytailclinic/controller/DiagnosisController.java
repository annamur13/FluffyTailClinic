package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.services.DiagnosisService;

import java.util.List;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    // Получить все диагнозы
    @GetMapping
    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisService.getAllDiagnoses();
    }

    // Получить диагноз по ID
    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getDiagnosisById(@PathVariable Long id) {
        return diagnosisService.getDiagnosisById(id)
                .map(diagnosis -> ResponseEntity.ok(diagnosis))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Создать новый диагноз
    @PostMapping
    public Diagnosis createDiagnosis(@RequestBody Diagnosis diagnosis) {
        return diagnosisService.createDiagnosis(diagnosis);
    }

    // Обновить диагноз
    @PutMapping("/{id}")
    public Diagnosis updateDiagnosis(@PathVariable Long id, @RequestBody Diagnosis diagnosisDetails) {
        return diagnosisService.updateDiagnosis(id, diagnosisDetails);
    }

    // Удалить диагноз
    @DeleteMapping("/{id}")
    public void deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
    }
}