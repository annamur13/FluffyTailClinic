package ru.ssau.towp.fluffytailclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.towp.fluffytailclinic.models.Appointment;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Long animalId;
    private Long vetId;
    private LocalDateTime date;
    private String description;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.animalId = appointment.getAnimal().getId();
        this.vetId = appointment.getVet().getId();
        this.date = appointment.getDate();
        this.description = appointment.getDescription();
    }

    /**
     * Возвращает время из поля date.
     *
     * @return время приёма.
     */
    public LocalTime getTime() {
        return date != null ? date.toLocalTime() : null;
    }

    /**
     * Устанавливает время в поле date.
     * Если date равно null, создаёт новый LocalDateTime с текущей датой и указанным временем.
     *
     * @param time время приёма.
     */
    public void setTime(LocalTime time) {
        if (time != null) {
            if (date == null) {
                date = LocalDateTime.now().with(time);
            } else {
                date = date.with(time);
            }
        }
    }
}