package ru.ssau.towp.fluffytailclinic.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Получить всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получить пользователя по ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
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

        user.setName(userDetails.getName());
        user.setPassword(userDetails.getPassword());
        user.setPhone(userDetails.getPhone());
        user.setEmail(userDetails.getEmail());
        user.setDescription(userDetails.getDescription());
        user.setRole(userDetails.getRole());

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