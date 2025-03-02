package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Получить пользователя по ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
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