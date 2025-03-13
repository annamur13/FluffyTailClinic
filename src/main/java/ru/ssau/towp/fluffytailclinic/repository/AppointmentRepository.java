package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Appointment;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query(value = "select a.*  from appointments as a where a.animal_id = :animal_id",
            nativeQuery = true)
    Optional<Appointment> findByAnimalId (@Param("animalId") Long animalId);

}
