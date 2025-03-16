package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiagnosisAppointment> diagnosisAppointments;

    // Конструктор по умолчанию
    public Appointment() {
    }

    // Конструктор без ID (для создания новых записей)
    public Appointment(Animal animal, User vet, LocalDateTime date, String description) {
        this.animal = animal;
        this.vet = vet;
        this.date = date;
        this.description = description;
    }

    // Конструктор с ID (для обновления существующих записей)
    public Appointment(Long id, Animal animal, User vet, LocalDateTime date, String description) {
        this.id = id;
        this.animal = animal;
        this.vet = vet;
        this.date = date;
        this.description = description;
    }

    // Метод для добавления диагноза к записи
    public void addDiagnosisAppointment(DiagnosisAppointment diagnosisAppointment) {
        diagnosisAppointments.add(diagnosisAppointment);
        diagnosisAppointment.setAppointment(this);
    }

    // Метод для удаления диагноза из записи
    public void removeDiagnosisAppointment(DiagnosisAppointment diagnosisAppointment) {
        diagnosisAppointments.remove(diagnosisAppointment);
        diagnosisAppointment.setAppointment(null);
    }

    // Метод для установки времени записи
    public void setTime(LocalTime time) {
        this.date = this.date.withHour(time.getHour()).withMinute(time.getMinute()).withSecond(0);
    }
}