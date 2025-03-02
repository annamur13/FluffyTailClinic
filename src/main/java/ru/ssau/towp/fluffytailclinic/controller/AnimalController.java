package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.services.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable Long id) {
        return animalService.getAnimalById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
    }

    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    @PutMapping("/{id}")
    public Animal updateAnimal(@PathVariable Long id, @RequestBody Animal animalDetails) {
        return animalService.updateAnimal(id, animalDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
    }
}