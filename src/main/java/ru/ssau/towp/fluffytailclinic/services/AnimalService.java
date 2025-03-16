package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Получить список всех животных.
     *
     * @return список всех животных
     */
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    /**
     * Найти животное по ID.
     *
     * @param id идентификатор животного
     * @return Optional с животным, если оно найдено
     */
    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    /**
     * Создать новое животное.
     *
     * @param animal данные животного
     * @return созданное животное
     */
    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    /**
     * Обновить данные животного.
     *
     * @param id           идентификатор животного
     * @param animalDetails новые данные животного
     * @return обновлённое животное
     * @throws RuntimeException если животное не найдено
     */
    public Animal updateAnimal(Long id, Animal animalDetails) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        animal.setName(animalDetails.getName());
        animal.setType(animalDetails.getType());
        animal.setOwner(animalDetails.getOwner()); // Если есть поле "владелец"
        return animalRepository.save(animal);
    }

    /**
     * Удалить животное по ID.
     *
     * @param id идентификатор животного
     */
    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    /**
     * Найти животное по ID (альтернативная версия с исключением).
     *
     * @param animalId идентификатор животного
     * @return найденное животное
     * @throws RuntimeException если животное не найдено
     */
    public Animal findByAnimalId(Long animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal with id " + animalId + " not found"));
    }
}