package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public abstract class UserService {

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

    public abstract boolean addUser(User user);

    public abstract String encryptPassword(String password);
}