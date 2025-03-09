package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;
import ru.ssau.towp.fluffytailclinic.services.AnimalService;
import ru.ssau.towp.fluffytailclinic.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnimalController(AnimalRepository animalRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    // 🔹 Получение всех животных
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    // 🔹 Получение животного по ID
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) {
        return animalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Создание нового животного
    @PostMapping
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        if (animal.getOwner() == null || animal.getOwner().getId() == null) {
            System.out.println("Errorrrr");
            return ResponseEntity.badRequest().build();
        }

        User owner = userRepository.findById(animal.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Владелец не найден"));

        animal.setOwner(owner);
        Animal savedAnimal = animalRepository.save(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnimal);
    }

    // 🔹 Обновление животного
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal updatedAnimal) {
        return animalRepository.findById(id)
                .map(animal -> {
                    animal.setName(updatedAnimal.getName());
                    animal.setType(updatedAnimal.getType());

                    if (updatedAnimal.getOwner() != null && updatedAnimal.getOwner().getId() != null) {
                        User owner = userRepository.findById(updatedAnimal.getOwner().getId())
                                .orElseThrow(() -> new RuntimeException("Владелец не найден"));
                        animal.setOwner(owner);
                    }

                    animalRepository.save(animal);
                    return ResponseEntity.ok(animal);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Удаление животного
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        if (!animalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        animalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}