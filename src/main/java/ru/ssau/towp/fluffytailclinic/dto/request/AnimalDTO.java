package ru.ssau.towp.fluffytailclinic.dto.request;

import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import ru.ssau.towp.fluffytailclinic.models.Animal;

@Data
public class AnimalDTO {
    private Long id;
    private String name;
    private String type;
    private Long ownerId; // Только ID владельца

    public AnimalDTO() {}

    public AnimalDTO(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.type = animal.getType();
        this.ownerId = animal.getOwner().getId();
    }
}
