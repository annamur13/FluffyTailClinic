package ru.ssau.towp.fluffytailclinic.dto;

import lombok.Data;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DiagnosisDTO {
    private Long id;
    private String name;
    private String description;
    private List<Long> DiagnosisAppointmentIds;

    public DiagnosisDTO() {}

    public DiagnosisDTO(Diagnosis diagnosis) {
        this.id = diagnosis.getId();
        this.name = diagnosis.getName();
        this.description = diagnosis.getDescription();
        this.DiagnosisAppointmentIds = diagnosis.getDiagnosisAppointments().stream()
                .map(DiagnosisAppointment::getId)
                .collect(Collectors.toList());;
    }
}
