package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import ru.ssau.towp.fluffytailclinic.controller.AppointmentController;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppointmentController appointmentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
                .andExpect(jsonPath("$", hasSize(appointmentList.size())))
                .andExpect(jsonPath("$[0].description", is("Checkup")))
                .andExpect(jsonPath("$[0].date", is("2023-10-01T10:00:00")))  // Проверка формата даты
                .andExpect(jsonPath("$[1].description", is("Vaccination")))
                .andExpect(jsonPath("$[2].description", is("Surgery")));
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
}