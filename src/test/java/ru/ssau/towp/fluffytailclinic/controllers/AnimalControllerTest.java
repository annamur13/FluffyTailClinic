package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ssau.towp.fluffytailclinic.controller.AnimalController;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.services.AnimalService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class) // Подключаем Mockito
public class AnimalControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AnimalService animalService; // Создаём мок-сервис

    @InjectMocks
    private AnimalController animalController; // Внедряем мок в контроллер

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Animal> animalList;

    @BeforeEach
    void setUp() {
        // Настраиваем MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(animalController).build();

        // Инициализируем тестовые данные
        Animal animal1 = new Animal(1L, "Buddy", "Dog");
        Animal animal2 = new Animal(2L, "Whiskers", "Cat");
        Animal animal3 = new Animal(3L, "Rex", "Dog");

        animalList = Arrays.asList(animal1, animal2, animal3);
    }

    @Test
    void shouldFetchAllAnimals() throws Exception {
        // Мокируем вызов сервиса
        given(animalService.getAllAnimals()).willReturn(animalList);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(animalList.size())));
    }

    @Test
    void shouldFindAnimalById() throws Exception {
        Long animalId = 1L;
        Animal animal = new Animal(animalId, "Buddy", "Dog");

        // Мокируем вызов сервиса
        given(animalService.getAnimalById(animalId)).willReturn(Optional.of(animal));

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/animals/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(animal.getName()))
                .andExpect(jsonPath("$.type").value(animal.getType()));

    }
    @Test
    void shouldReturnNotFoundIfAnimalDoesNotExist() throws Exception {
        Long animalId = 99L;

        given(animalService.getAnimalById(animalId)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/animals/{id}", animalId))
                .andExpect(status().isNotFound());
    }
}
