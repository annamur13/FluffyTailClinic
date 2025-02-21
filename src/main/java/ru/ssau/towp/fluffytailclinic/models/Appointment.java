package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    private User vet;

    private LocalDateTime date;
    private String description;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<DiagnosisAppointment> diagnosisAppointments;
}

