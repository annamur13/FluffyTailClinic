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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {this.date = date; }

    public User getVet() {
        return vet;
    }

    public void setVet(User vet) {this.vet = vet; }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {this.animal = animal;    }
}

