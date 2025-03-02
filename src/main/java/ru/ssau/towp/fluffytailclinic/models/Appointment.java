package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    public Object getDescription() {
        return null;
    }

    public void setDescription(Object description) {
    }

    public Object getDate() {
        return null;
    }

    public void setDate(Object date) {
    }

    public Object getVet() {
        return null;
    }

    public void setVet(Object vet) {
    }

    public Object getAnimal() {
        return null;
    }

    public void setAnimal(Object animal) {
    }
}

