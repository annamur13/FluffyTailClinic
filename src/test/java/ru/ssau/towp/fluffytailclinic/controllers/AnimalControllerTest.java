package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ssau.towp.fluffytailclinic.controller.AnimalController;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.services.AnimalService;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnimalController.class)

public class AnimalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AnimalService studentServices;
    @Autowired
    private ObjectMapper objectMapper;
    private Object animalList;

    @BeforeEach
    void setUp() {

        animalList.add(new Animal(1L, "user1", "user1@gmail.com", 20));
        animalList.add(new Animal(2L, "user2", "user2@gmail.com", 21));
        animalList.add(new Animal(3L, "user3", "user3@gmail.com", 22));

        //objectMapper.registerModule(new ProblemM);
        //objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @Test
    void shouldFetchAllUsers() throws Exception {
        given(studentServices.findAllnimals()).willReturn(this.animalList);

        this.mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andReturn();

        this.mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(animalList.size())));

    }

    @Test
    void shouldFindStudentById() throws Exception {
        Long animalId = 1L;
        Animal animal = new Animal(1L, "user1", "user1@gmail.com", 20);
        given(AnimalService.findByAnimalId(animalId)).willReturn(Optional.of(animal));

        this.mockMvc.perform(get("/api/students/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(animal.getName()))
                .andExpect(jsonPath("$.age").value(animal.getType()))
                .andExpect(jsonPath("$.name").value(animal.getName()));
    }
}
