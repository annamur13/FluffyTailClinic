package ru.ssau.towp.fluffytailclinic.dto;

import lombok.Data;
import ru.ssau.towp.fluffytailclinic.models.DiagnosisAppointment;

@Data
public class DiagnosisAppointmentDTO {
    private Long id;
    private Long appointmentId;
    private Long diagnosisId;

    public DiagnosisAppointmentDTO() {}

    public DiagnosisAppointmentDTO(DiagnosisAppointment diagnosisAppointment) {
        this.id = diagnosisAppointment.getId();
        this.appointmentId = diagnosisAppointment.getAppointment().getId();
        this.diagnosisId = diagnosisAppointment.getDiagnosis().getId();
    }

}

