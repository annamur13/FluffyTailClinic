package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.dto.AnimalDTO;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.services.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {this.animalService = animalService;}

    // 🔹 Получение всех животных
    @GetMapping
    public List<AnimalDTO> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    // 🔹 Получение животного по ID
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimalById(@PathVariable Long id) {
        return animalService.getAnimalById(id);
    }

    // 🔹 Создание нового животного
    @PostMapping
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    // 🔹 Обновление животного
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal updatedAnimal) {
        return animalService.updateAnimal(id, updatedAnimal);
    }

    // 🔹 Удаление животного
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        return animalService.deleteAnimal(id);
    }
}