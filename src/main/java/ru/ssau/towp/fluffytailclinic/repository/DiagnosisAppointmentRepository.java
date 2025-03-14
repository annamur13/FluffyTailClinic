package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;

import java.util.List;

public interface DiagnosisAppointmentRepository extends JpaRepository<DiagnosisAppointment, Long> {
    List<DiagnosisAppointment> findByAppointmentId(Long appointmentId);
}
