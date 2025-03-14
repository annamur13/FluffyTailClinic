package ru.ssau.towp.fluffytailclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.towp.fluffytailclinic.models.Appointment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Long animalId;
    private Long vetId;
    private LocalDateTime date;
    private String description;

    public AppointmentDTO(){}

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.animalId = appointment.getAnimal().getId();
        this.vetId = appointment.getVet().getId();
        this.date = appointment.getDate();
        this.description = appointment.getDescription();
    }
}

