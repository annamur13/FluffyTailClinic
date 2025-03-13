package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;

import java.util.Optional;

public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {
    @Query(value = "select * from diagnoses as c where c.name = :name",
            nativeQuery = true)
    Optional<Diagnosis> findByName(@Param("name") String name);

}
