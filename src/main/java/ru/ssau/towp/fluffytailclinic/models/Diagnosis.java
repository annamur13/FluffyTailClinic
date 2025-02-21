package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "diagnoses")
@Getter
@Setter
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diagnosis_id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "diagnosis", cascade = CascadeType.ALL)
    private List<DiagnosisAppointment> diagnosisAppointments;

    public Diagnosis() {}
}

