package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    // Получить все диагнозы
    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    // Получить диагноз по ID
    public Optional<Diagnosis> getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id);
    }

    // Создать новый диагноз
    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
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
    public void deleteDiagnosis(Long id) {
        diagnosisRepository.deleteById(id);
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
}