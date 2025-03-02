package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisAppointmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisAppointmentService {

    @Autowired
    private DiagnosisAppointmentRepository diagnosisAppointmentRepository;

    // Получить все связи диагнозов и записей
    public List<DiagnosisAppointment> getAllDiagnosisAppointments() {
        return diagnosisAppointmentRepository.findAll();
    }

    // Получить связь по ID
    public Optional<DiagnosisAppointment> getDiagnosisAppointmentById(Long id) {
        return diagnosisAppointmentRepository.findById(id);
    }

    // Создать новую связь
    public DiagnosisAppointment createDiagnosisAppointment(DiagnosisAppointment diagnosisAppointment) {
        return diagnosisAppointmentRepository.save(diagnosisAppointment);
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
    public void deleteDiagnosisAppointment(Long id) {
        diagnosisAppointmentRepository.deleteById(id);
    }
}