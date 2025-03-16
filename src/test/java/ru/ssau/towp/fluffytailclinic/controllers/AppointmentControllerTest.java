package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ssau.towp.fluffytailclinic.controller.AppointmentController;
import ru.ssau.towp.fluffytailclinic.controller.UserController;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AppointmentController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private UserRepository userRepository;

    @MockitoBean
    private AppointmentController appointmentController;

    private Animal animal;
    private User vet;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        animal = new Animal();
        animal.setId(1L);
        animal.setName("Buddy");

        vet = new User();
        vet.setId(1L);
        vet.setName("Dr. Smith");

        // Настройка ObjectMapper для корректной сериализации LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Настройка MockMvc с использованием настроенного ObjectMapper
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void shouldFetchAllAppointments() throws Exception {
        // Подготовка данных
        List<Appointment> appointmentList = Arrays.asList(
                new Appointment(1L, animal, vet, LocalDateTime.of(2023, 10, 1, 10, 0), "Checkup"),
                new Appointment(2L, animal, vet, LocalDateTime.of(2023, 10, 2, 11, 0), "Vaccination"),
                new Appointment(3L, animal, vet, LocalDateTime.of(2023, 10, 3, 12, 0), "Surgery")
        );

        // Мокируем вызов репозитория
        given(appointmentRepository.findAll()).willReturn(appointmentList);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(appointmentList.size())))
                .andDo(print());
                //.andExpect(jsonPath("$",hasSize(3)));
                //.andExpect(jsonPath("description").value("Checkup"))
                //.andExpect(jsonPath("[0].description").value("Checkup"));
                //.andReturn().getResponse().getContentAsString();

//        .andExpect(jsonPath("$", hasSize(appointmentList.size())))
//                .andExpect(jsonPath("$[0].description", is("Checkup")))
//                .andExpect(jsonPath("$[0].date", is("2023-10-01T10:00:00")))  // Проверка формата даты
//                .andExpect(jsonPath("$[1].description", is("Vaccination")))
//                .andExpect(jsonPath("$[2].description", is("Surgery")));

    }

    @Test
    void shouldFindAppointmentById() throws Exception {
        // Подготовка данных
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(appointmentId, animal, vet, LocalDateTime.of(2023, 10, 1, 10, 0), "Checkup");

        // Мокируем вызов репозитория
        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.of(appointment));

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(appointmentId.intValue())))
                .andExpect(jsonPath("$.description", is("Checkup")))
                .andExpect(jsonPath("$.date", is("2023-10-01T10:00:00")));  // Проверка формата даты
    }

    @Test
    void shouldReturnNotFoundIfAppointmentDoesNotExist() throws Exception {
        // Подготовка данных
        Long appointmentId = 99L;

        // Мокируем вызов репозитория
        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.empty());

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/appointments/{id}", appointmentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateAppointment() throws Exception {
        // Подготовка данных
        Long animalId = 1L;
        Long vetId = 1L;
        LocalDateTime appointmentDate = LocalDateTime.of(2023, 10, 1, 10, 0);
        String description = "Checkup";

        Animal animal = new Animal(animalId, "Buddy", "Dog", new User(1L, "John Doe", "john@example.com"));
        User vet = new User(vetId, "Dr. Smith", "smith@example.com");

        // Назначение перед сохранением (без ID)
        Appointment newAppointment = new Appointment(null, animal, vet, appointmentDate, description);

        // Назначение после сохранения (с ID)
        Appointment savedAppointment = new Appointment(1L, animal, vet, appointmentDate, description);

        // Мокируем репозитории
        given(animalRepository.findById(animalId)).willReturn(Optional.of(animal));
        given(userRepository.findById(vetId)).willReturn(Optional.of(vet));
        given(appointmentRepository.save(Mockito.any(Appointment.class))).willReturn(savedAppointment);

        // JSON-объект для запроса (имитируем реальный формат)
        String appointmentJson = """
                {
                    "animal": {
                        "id": 1
                    },
                    "vet": {
                        "id": 1
                    },
                    "date": "2023-10-01T10:00:00",
                    "description": "Checkup"
                }
                """;

        // Отправляем запрос
        mockMvc.perform(post("/api/appointments")
                        .contentType("application/json")
                        .content(appointmentJson))
                .andExpect(status().isCreated()) // Ожидаем 201 Created
                .andExpect(jsonPath("$.id").value(1L)) // Проверяем ID
                .andExpect(jsonPath("$.description").value("Checkup")) // Проверяем описание
                .andExpect(jsonPath("$.date").value("2023-10-01T10:00:00")); // Проверяем дату

        // Проверяем, что save() был вызван с корректным Appointment (через ArgumentCaptor)
        ArgumentCaptor<Appointment> captor = ArgumentCaptor.forClass(Appointment.class);
        Mockito.verify(appointmentRepository).save(captor.capture());

        Appointment capturedAppointment = captor.getValue();
        assertEquals(animal, capturedAppointment.getAnimal());
        assertEquals(vet, capturedAppointment.getVet());
        assertEquals(appointmentDate, capturedAppointment.getDate());
        assertEquals(description, capturedAppointment.getDescription());
    }

    @Test
    void shouldUpdateAppointment() throws Exception {
        // Подготовка данных
        Long appointmentId = 1L;
        Animal animal = new Animal(1L, "Buddy", "Dog", new User(1L, "John Doe", "john@example.com"));
        User vet = new User(2L, "Dr. Smith", "smith@example.com");
        LocalDateTime appointmentDate = LocalDateTime.of(2023, 10, 1, 10, 0);
        String description = "Checkup";

        // Существующее назначение
        Appointment existingAppointment = new Appointment(appointmentId, animal, vet, appointmentDate, description);

        // Обновленное назначение
        LocalDateTime updatedDate = LocalDateTime.of(2023, 10, 2, 11, 0);
        String updatedDescription = "Vaccination";
        Appointment updatedAppointment = new Appointment(appointmentId, animal, vet, updatedDate, updatedDescription);

        // Мокируем вызовы репозиториев
        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.of(existingAppointment));
        given(appointmentRepository.save(existingAppointment)).willReturn(updatedAppointment);

        // Сериализуем объект Appointment в JSON
        String appointmentJson = objectMapper.writeValueAsString(updatedAppointment);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(put("/api/appointments/{id}", appointmentId)
                        .contentType("application/json")
                        .content(appointmentJson)) // Передаём JSON в теле запроса
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.id").value(appointmentId)) // Проверяем ID
                .andExpect(jsonPath("$.description").value(updatedDescription)) // Проверяем описание
                .andExpect(jsonPath("$.date").value(updatedDate.toString())); // Проверяем дату
    }

    @Test
    void shouldDeleteAppointment() throws Exception {
        // Подготовка данных
        Long appointmentId = 1L;

        // Мокируем вызов репозитория
        given(appointmentRepository.existsById(appointmentId)).willReturn(true);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(delete("/api/appointments/{id}", appointmentId))
                .andExpect(status().isNoContent()); // Ожидаем статус 204 No Content

        // Проверяем, что метод репозитория был вызван
        Mockito.verify(appointmentRepository).deleteById(appointmentId);
    }
}