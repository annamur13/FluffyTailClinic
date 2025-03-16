package ru.ssau.towp.fluffytailclinic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.controller.NF.ResourceNotFoundException;
import ru.ssau.towp.fluffytailclinic.dto.UserDTO;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;
import ru.ssau.towp.fluffytailclinic.services.AnimalService;
import ru.ssau.towp.fluffytailclinic.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;
    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;

    @Autowired
    public UserController(AnimalRepository animalRepository, UserRepository userRepository, UserService userService) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    // Получить всех пользователей
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }


    // Получить пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return ResponseEntity.ok(new UserDTO(user));
    }

    // Получить пользователя по email
    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // Создать нового пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Обновить пользователя
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}