package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ssau.towp.fluffytailclinic.controller.DiagnosisController;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.services.DiagnosisService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DiagnosisControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DiagnosisService diagnosisService; // Мокируем DiagnosisService

    @InjectMocks
    private DiagnosisController diagnosisController; // Внедряем моки в контроллер

    private List<Diagnosis> diagnosisList;

    @BeforeEach
    void setUp() {
        // Настроим MockMvc для тестирования контроллера
        mockMvc = MockMvcBuilders.standaloneSetup(diagnosisController).build();

        // Инициализируем тестовые данные для Diagnosis
        Diagnosis diagnosis1 = new Diagnosis("Diagnosis 1", "Description 1");
        Diagnosis diagnosis2 = new Diagnosis("Diagnosis 2", "Description 2");
        Diagnosis diagnosis3 = new Diagnosis("Diagnosis 3", "Description 3");

        diagnosisList = Arrays.asList(diagnosis1, diagnosis2, diagnosis3);
    }

    @Test
    void shouldFetchAllDiagnoses() throws Exception {
        // Мокируем вызов сервиса
        given(diagnosisService.getAllDiagnoses()).willReturn(diagnosisList);

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/diagnoses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(diagnosisList.size())));
    }


    @Test
    void shouldFindDiagnosisById() throws Exception {
        Long diagnosisId = 1L;
        Diagnosis diagnosis = new Diagnosis("Diagnosis 1", "Description 1");
        diagnosis.setId(diagnosisId);

        // Мокируем вызов сервиса
        given(diagnosisService.getDiagnosisById(diagnosisId)).willReturn(Optional.of(diagnosis));

        // Выполняем запрос и проверяем результат
        mockMvc.perform(get("/api/diagnoses/{id}", diagnosisId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(diagnosis.getName()))
                .andExpect(jsonPath("$.description").value(diagnosis.getDescription()));
    }

    @Test
    void shouldReturnNotFoundWhenDiagnosisNotFound() throws Exception {
        Long diagnosisId = 1L;

        // Мокируем вызов сервиса для несуществующего диагноза
        given(diagnosisService.getDiagnosisById(diagnosisId)).willReturn(Optional.empty());

        // Выполняем запрос и проверяем результат (ожидаем 404)
        mockMvc.perform(get("/api/diagnoses/{id}", diagnosisId))
                .andExpect(status().isNotFound());
    }

}
