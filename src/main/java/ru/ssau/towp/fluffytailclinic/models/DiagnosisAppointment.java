package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diagnosis_appointment")
@Getter
@Setter
public class DiagnosisAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diagApp_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;

    public DiagnosisAppointment() {}

    public Object getAppointment() {
        return null;
    }

    public void setAppointment(Object appointment) {
    }

    public Object getDiagnosis() {
        return null;
    }

    public void setDiagnosis(Object diagnosis) {
    }
}
