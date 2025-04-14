package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Поиск по ID животного
    @Query(value = "SELECT a.* FROM appointments AS a WHERE a.animal_id = :animalId", nativeQuery = true)
    Optional<Appointment> findByAnimalId(@Param("animalId") Long animalId);

    // Поиск по ветеринару
    @Query(value = "SELECT a.* FROM appointments AS a WHERE a.vet_id = :vetId", nativeQuery = true)
    List<Appointment> findByVet(@Param("vetId") Long vetId);

    // Поиск по дате и времени
    @Query(value = "SELECT a.* FROM appointments AS a WHERE a.date = :date", nativeQuery = true)
    List<Appointment> findByDate(@Param("date") LocalDateTime date);

    //Поиск по владельцу
    @Query("SELECT a FROM Appointment a WHERE a.animal.owner = :user")
    List<Appointment> findByAnimalOwner(@Param("user") User user);


}