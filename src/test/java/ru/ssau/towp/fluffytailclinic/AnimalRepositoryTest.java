package ru.ssau.towp.fluffytailclinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;

public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    Animal animal;


    @Test
    void givenAnimal_whenSaved_thenCanBeFoundById() {

        Animal savedAnimal = animalRepository.findById(animal.getId()).orElse(null);
        Assertions.assertNotNull(savedAnimal);

        Assertions.assertEquals(animal.getName(), savedAnimal.getName());
        Assertions.assertEquals(animal.getType(), savedAnimal.getType());
        Assertions.assertEquals(animal.getOwner(), savedAnimal.getOwner());
    }
}
