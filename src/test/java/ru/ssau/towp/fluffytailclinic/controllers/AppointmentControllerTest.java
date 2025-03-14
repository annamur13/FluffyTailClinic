package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ssau.towp.fluffytailclinic.controller.AppointmentController;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.services.AppointmentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppointmentController.class) // Тестируем AppointmentController
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppointmentService appointmentService; // Используем AppointmentService

    @Autowired
    private ObjectMapper objectMapper;

    private List<Appointment> appointmentList;

    @BeforeEach
    void setUp() {
        // Инициализируем тестовые данные для Appointment
        appointmentList = Arrays.asList(
                new Appointment(1L, "2023-10-01", "10:00", "Checkup"),
                new Appointment(2L, "2023-10-02", "11:00", "Vaccination"),
                new Appointment(3L, "2023-10-03", "12:00", "Surgery")
        );
    }

    @Test
    void shouldFetchAllAppointments() throws Exception {
        // Мокируем вызов сервиса
        given(appointmentService.findAllAppointments()).willReturn(appointmentList);

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(appointmentList.size())));
    }

    @Test
    void shouldFindAppointmentById() throws Exception {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment(1L, "2023-10-01", "10:00", "Checkup");

        // Мокируем вызов сервиса
        given(appointmentService.findAppointmentById(appointmentId)).willReturn(Optional.of(appointment));

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(appointment.getDate()))
                .andExpect(jsonPath("$.time").value(appointment.getTime()))
                .andExpect(jsonPath("$.description").value(appointment.getDescription()));
    }
}