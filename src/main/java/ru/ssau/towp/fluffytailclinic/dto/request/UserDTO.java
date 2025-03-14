package ru.ssau.towp.fluffytailclinic.dto.request;

import lombok.Data;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.User;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<Long> animalIds;

    public UserDTO() {} // Пустой конструктор нужен для Jackson

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.animalIds = user.getAnimals().stream()
                .map(Animal::getId)
                .collect(Collectors.toList());
    }
}
