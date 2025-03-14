package ru.ssau.towp.fluffytailclinic.dto;

public class DiagnosisAppointmentDTO {
    private Long id;
    private Long appointmentId;
    private Long diagnosisId;

    public DiagnosisAppointmentDTO() {}

    public DiagnosisAppointmentDTO(Long id, Long appointmentId, Long diagnosisId, String notes) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.diagnosisId = diagnosisId;
    }

    public Long getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

}

