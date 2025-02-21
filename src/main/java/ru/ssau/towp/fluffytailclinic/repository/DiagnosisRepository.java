package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;

public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {
}
