package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ssau.towp.fluffytailclinic.controller.NF.ResourceNotFoundException;
import ru.ssau.towp.fluffytailclinic.dto.DiagnosisDTO;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    // Получить все диагнозы
    public List<DiagnosisDTO> getAllDiagnoses() {
        return diagnosisRepository.findAll().stream()
                .map(DiagnosisDTO::new)
                .collect(Collectors.toList());
    }

    // Получить диагноз по ID
    public ResponseEntity<DiagnosisDTO> getDiagnosisById(@PathVariable Long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));
        return ResponseEntity.ok(new DiagnosisDTO(diagnosis));
    }

    // Создать новый диагноз
    public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) {
        Diagnosis savedDiagnosis = diagnosisRepository.save(diagnosis);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDiagnosis);
    }

    // Обновить диагноз
    public Diagnosis updateDiagnosis(Long id, Diagnosis diagnosisDetails) {
        Diagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));

        diagnosis.setName(diagnosisDetails.getName());
        diagnosis.setDescription(diagnosisDetails.getDescription());

        return diagnosisRepository.save(diagnosis);
    }

    // Удалить диагноз
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        diagnosisRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Получить все диагнозы (альтернативное название метода)
    public List<Diagnosis> findAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    // Получить диагноз по ID (альтернативное название метода)
    public Optional<Diagnosis> findDiagnosisById(Long diagnosisId) {
        return diagnosisRepository.findById(diagnosisId);
    }

    // Получить диагноз по имени
    public Optional<Diagnosis> findDiagnosisByName(String diagnosisName) {
        return diagnosisRepository.findByName(diagnosisName);
    }

    public boolean existsById(Long id) {
        return diagnosisRepository.existsById(id);
    }
}