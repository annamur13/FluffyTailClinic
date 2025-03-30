package ru.ssau.towp.fluffytailclinic.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ru.ssau.towp.fluffytailclinic.dto.AppointmentDTO;
import ru.ssau.towp.fluffytailclinic.dto.UserDTO;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;

    // Получить всех пользователей
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // Получить пользователя по ID
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return ResponseEntity.ok(new UserDTO(user));
    }

    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<AppointmentDTO> appointments = appointmentRepository.findByAnimalOwner(user)
                .stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(appointments);
    }

    // Получить пользователя по email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Получить пользователя по имени
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    // Получить пользователя по номеру телефона
    public Optional<User> getUserByPhone(String userPhone) {
        return userRepository.findByPhone(userPhone);
    }

    // Создать нового пользователя
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Обновить пользователя
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDetails.getName() != null) user.setName(userDetails.getName());
        if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null) user.setPassword(userDetails.getPassword());

        return userRepository.save(user);
    }

    // Удалить пользователя
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Добавить пользователя (альтернативное название метода)
    public boolean addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false; // Пользователь с таким email уже существует
        }
        userRepository.save(user);
        return true;
    }

    // Шифрование пароля (заглушка, можно реализовать с использованием BCrypt)
    public String encryptPassword(String password) {
        // Пример простого шифрования (в реальном проекте используйте BCryptPasswordEncoder)
        return new StringBuilder(password).reverse().toString();
    }
}