package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ssau.towp.fluffytailclinic.controller.AnimalController;
import ru.ssau.towp.fluffytailclinic.dto.AnimalDTO;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AnimalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnimalRepository animalRepository; // Мокируем AnimalRepository

    @Mock
    private UserRepository userRepository; // Мокируем UserRepository

    @InjectMocks
    private AnimalController animalController; // Внедряем моки в контроллер

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Animal> animalList;

    @BeforeEach
    void setUp() {
        // Настраиваем MockMvc для тестирования контроллера
        mockMvc = MockMvcBuilders.standaloneSetup(animalController).build();

        // Инициализируем тестовые данные
        User owner = new User(1L, "John Doe", "john@example.com");
        Animal animal1 = new Animal(1L, "Buddy", "Dog", owner);
        Animal animal2 = new Animal(2L, "Whiskers", "Cat", owner);
        Animal animal3 = new Animal(3L, "Rex", "Dog", owner);

        animalList = Arrays.asList(animal1, animal2, animal3);
    }

    @Test
    void shouldFetchAllAnimals() throws Exception {
        // Мокируем вызов репозитория
        given(animalRepository.findAll()).willReturn(animalList);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(animalList.size())))
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[1].name").value("Whiskers"))
                .andExpect(jsonPath("$[2].name").value("Rex"));
    }

    @Test
    void shouldFindAnimalById() throws Exception {
        Long animalId = 1L;
        User owner = new User(1L, "John Doe", "john@example.com");
        Animal animal = new Animal(animalId, "Buddy", "Dog", owner);

        // Мокируем вызов репозитория
        given(animalRepository.findById(animalId)).willReturn(Optional.of(animal));

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/animals/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.type").value("Dog"));
    }

    @Test
    void shouldReturnNotFoundIfAnimalDoesNotExist() throws Exception {
        Long animalId = 99L;

        // Мокируем вызов репозитория
        given(animalRepository.findById(animalId)).willReturn(Optional.empty());

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/animals/{id}", animalId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateAnimal() throws Exception {
        // Подготовка данных
        Long ownerId = 1L;
        User owner = new User(ownerId, "John Doe", "john@example.com");

        // Животное перед сохранением (без ID)
        Animal newAnimal = new Animal(null, "Max", "Dog", owner);

        // Животное после сохранения (с ID)
        Animal savedAnimal = new Animal(4L, "Max", "Dog", owner);

        // Мокируем репозиторий пользователей
        given(userRepository.findById(ownerId)).willReturn(Optional.of(owner));

        // Мокируем сохранение животного (важно: аргумент не должен сравниваться по ссылке!)
        given(animalRepository.save(Mockito.any(Animal.class))).willReturn(savedAnimal);

        // JSON-объект для запроса (имитируем реальный формат)
        String animalJson = """
        {
            "name": "Max",
            "type": "Dog",
            "owner": {
                "id": 1
            }
        }
    """;

        // Отправляем запрос
        mockMvc.perform(post("/api/animals")
                        .contentType("application/json")
                        .content(animalJson))
                .andExpect(status().isCreated()) // Ожидаем 201 Created
                .andExpect(jsonPath("$.id").value(4L)) // Проверяем ID
                .andExpect(jsonPath("$.name").value("Max")) // Проверяем имя
                .andExpect(jsonPath("$.type").value("Dog")); // Проверяем тип

        // Проверяем, что save() был вызван с корректным Animal (через ArgumentCaptor)
        ArgumentCaptor<Animal> captor = ArgumentCaptor.forClass(Animal.class);
        Mockito.verify(animalRepository).save(captor.capture());

        Animal capturedAnimal = captor.getValue();
        assertEquals("Max", capturedAnimal.getName());
        assertEquals("Dog", capturedAnimal.getType());
        assertEquals(owner, capturedAnimal.getOwner());
    }


    @Test
    void shouldUpdateAnimal() throws Exception {
        Long animalId = 1L;
        User owner = new User(1L, "John Doe", "john@example.com");
        Animal existingAnimal = new Animal(animalId, "Buddy", "Dog", owner);
        Animal updatedAnimal = new Animal(animalId, "Buddy Updated", "Dog", owner);

        // Мокируем вызовы репозиториев
        given(animalRepository.findById(animalId)).willReturn(Optional.of(existingAnimal));
        given(animalRepository.save(existingAnimal)).willReturn(updatedAnimal);

        // Сериализуем объект Animal в JSON
        String animalJson = objectMapper.writeValueAsString(updatedAnimal);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(put("/api/animals/{id}", animalId)
                        .contentType("application/json")
                        .content(animalJson)) // Передаём JSON в теле запроса
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.name").value("Buddy Updated")) // Проверяем имя
                .andExpect(jsonPath("$.type").value("Dog")); // Проверяем тип
    }

    @Test
    void shouldDeleteAnimal() throws Exception {
        Long animalId = 1L;

        // Мокируем вызов репозитория
        given(animalRepository.existsById(animalId)).willReturn(true);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(delete("/api/animals/{id}", animalId))
                .andExpect(status().isNoContent());

        // Проверяем, что метод репозитория был вызван
        Mockito.verify(animalRepository).deleteById(animalId);
    }
}