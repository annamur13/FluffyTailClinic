package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ssau.towp.fluffytailclinic.controller.NF.ResourceNotFoundException;
import ru.ssau.towp.fluffytailclinic.dto.AnimalDTO;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
        private final UserRepository userRepository;

    @Autowired
    public AnimalService(AnimalRepository animalRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<Animal>> getUserPets(@RequestParam Long userId) {
        List<Animal> pets = animalRepository.findByOwnerId(userId);
        return ResponseEntity.ok(pets);
    }

    public List<AnimalDTO> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(AnimalDTO::new)
                .collect(Collectors.toList());
    }

    public ResponseEntity<AnimalDTO> getAnimalById(@PathVariable Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Животное с ID " + id + " не найдено"));
        return ResponseEntity.ok(new AnimalDTO(animal));
    }

    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        System.out.println("Полученные данные: " + animal);

        if (animal == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (animal.getOwner() == null || animal.getOwner().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        User owner = userRepository.findById(animal.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Владелец не найден"));

        animal.setOwner(owner);
        Animal savedAnimal = animalRepository.save(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnimal);
    }

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

    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        if (!animalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        animalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}