package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;

public interface DiagnosisAppointmentRepository extends JpaRepository<DiagnosisAppointment,Long> {
}
