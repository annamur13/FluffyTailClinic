package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ssau.towp.fluffytailclinic.controller.NF.ResourceNotFoundException;
import ru.ssau.towp.fluffytailclinic.dto.DiagnosisAppointmentDTO;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisAppointmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiagnosisAppointmentService {

    @Autowired
    private DiagnosisAppointmentRepository diagnosisAppointmentRepository;

    // Получить все связи диагнозов и записей
    public List<DiagnosisAppointmentDTO> getAllDiagnosisAppointments() {
        return diagnosisAppointmentRepository.findAll().stream()
                .map(DiagnosisAppointmentDTO::new)
                .collect(Collectors.toList());
    }
    // Получить связь по ID
    public ResponseEntity<DiagnosisAppointmentDTO> getDiagnosisAppointmentById(@PathVariable Long id) {
        DiagnosisAppointment diagnosisAppointment = diagnosisAppointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DiagnosisAppointment not found"));
        return ResponseEntity.ok(new DiagnosisAppointmentDTO(diagnosisAppointment));
    }

    // Создать новую связь
    public ResponseEntity<DiagnosisAppointment> createDiagnosisAppointment(@RequestBody DiagnosisAppointment diagnosisAppointment) {
        if (diagnosisAppointment.getAppointment() == null || diagnosisAppointment.getDiagnosis() == null) {
            return ResponseEntity.badRequest().build();
        }

        DiagnosisAppointment createdDiagnosisAppointment = diagnosisAppointmentRepository.save(diagnosisAppointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiagnosisAppointment);
    }

    // Обновить связь
    public DiagnosisAppointment updateDiagnosisAppointment(Long id, DiagnosisAppointment diagnosisAppointmentDetails) {
        DiagnosisAppointment diagnosisAppointment = diagnosisAppointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DiagnosisAppointment not found"));

        diagnosisAppointment.setAppointment(diagnosisAppointmentDetails.getAppointment());
        diagnosisAppointment.setDiagnosis(diagnosisAppointmentDetails.getDiagnosis());

        return diagnosisAppointmentRepository.save(diagnosisAppointment);
    }

    // Удалить связь
    public ResponseEntity<Void> deleteDiagnosisAppointment(@PathVariable Long id) {
        diagnosisAppointmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}