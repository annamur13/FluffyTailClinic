package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ssau.towp.fluffytailclinic.models.Animal;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal,Long> {

    @Query(value = "SELECT * FROM animals WHERE owner_id = :userId", nativeQuery = true)
    List<Animal> findByOwnerId(Long userId);
}
