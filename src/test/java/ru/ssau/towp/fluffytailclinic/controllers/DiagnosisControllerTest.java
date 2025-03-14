package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ssau.towp.fluffytailclinic.controller.DiagnosisController;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.services.DiagnosisService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DiagnosisController.class) // Тестируем DiagnosisController
public class DiagnosisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DiagnosisService diagnosisService; // Используем DiagnosisService

    @Autowired
    private ObjectMapper objectMapper;

    private List<Diagnosis> diagnosisList;

    @BeforeEach
    void setUp() {
        // Инициализируем тестовые данные для Diagnosis
        Diagnosis diagnosis1 = new Diagnosis("Diagnosis 1", "Description 1");
        Diagnosis diagnosis2 = new Diagnosis("Diagnosis 2", "Description 2");
        Diagnosis diagnosis3 = new Diagnosis("Diagnosis 3", "Description 3");

        diagnosisList = Arrays.asList(diagnosis1, diagnosis2, diagnosis3);
    }

    @Test
    void shouldFetchAllDiagnoses() throws Exception {
        // Мокируем вызов сервиса
        given(diagnosisService.findAllDiagnoses()).willReturn(diagnosisList);

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/diagnoses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(diagnosisList.size())));
    }

    @Test
    void shouldFindDiagnosisById() throws Exception {
        Long diagnosisId = 1L;
        Diagnosis diagnosis = new Diagnosis("Diagnosis 1", "Description 1");
        diagnosis.setId(diagnosisId);

        // Мокируем вызов сервиса
        given(diagnosisService.findDiagnosisById(diagnosisId)).willReturn(Optional.of(diagnosis));

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/diagnoses/{id}", diagnosisId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(diagnosis.getName()))
                .andExpect(jsonPath("$.description").value(diagnosis.getDescription()));
    }

    @Test
    void shouldFindDiagnosisByName() throws Exception {
        String diagnosisName = "Diagnosis 1";
        Diagnosis diagnosis = new Diagnosis(diagnosisName, "Description 1");

        // Мокируем вызов сервиса
        given(diagnosisService.findDiagnosisByName(diagnosisName)).willReturn(Optional.of(diagnosis));

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/diagnoses/name/{name}", diagnosisName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(diagnosis.getName()))
                .andExpect(jsonPath("$.description").value(diagnosis.getDescription()));
    }
}