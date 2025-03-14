package ru.ssau.towp.fluffytailclinic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.RoleRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Animal animal;


    @BeforeEach
    public void setUp() {
        // Инициализация тестовых данных перед каждым тестом
        Role role = new Role("Owner");
        roleRepository.save(role);

        User owner = new User();
        owner.setName("John Doe"); // Устанавливаем имя владельца
        owner.setRole(role);
        userRepository.save(owner);

        animal = new Animal();
        animal.setName("Buddy");
        animal.setType("Dog");
        animal.setOwner(owner);

        // Сохраняем животное в репозиторий
        animalRepository.save(animal);
    }

    @AfterEach
    public void tearDown() {
        // Очистка тестовых данных после каждого теста
        animalRepository.delete(animal);
    }

    @Test
    void givenAnimal_whenSaved_thenCanBeFoundById() {
        // Поиск животного по ID
        Animal savedAnimal = animalRepository.findById(animal.getId()).orElse(null);
        assertNotNull(savedAnimal);

        // Проверка, что данные совпадают
        assertEquals(animal.getName(), savedAnimal.getName());
        assertEquals(animal.getType(), savedAnimal.getType());
        assertEquals(animal.getOwner().getId(), savedAnimal.getOwner().getId());
    }
}