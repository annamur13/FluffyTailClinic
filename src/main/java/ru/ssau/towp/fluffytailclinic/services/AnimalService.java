package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.*;
import lombok.*;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Long id, Animal animalDetails) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
        animal.setName(animalDetails.getName());
        animal.setType(animalDetails.getType());
        animal.setOwner(animalDetails.getOwner());
        return animalRepository.save(animal);
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }
}