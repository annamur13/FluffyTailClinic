package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter
@Setter
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

    @Column(name="date")
    private LocalDateTime date;
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<DiagnosisAppointment> diagnosisAppointments;

    public Appointment(){}

    public Appointment(long l, String date, String time, String checkup) {
    }

    private LocalTime time;
    public Object getTime() {
        return this.time;
    }
}

