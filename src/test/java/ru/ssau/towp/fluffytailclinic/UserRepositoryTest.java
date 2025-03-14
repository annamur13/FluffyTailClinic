package ru.ssau.towp.fluffytailclinic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.RoleRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        // Инициализация тестовых данных перед каждым тестом
        role = new Role("Owner"); // Создаем роль
        roleRepository.save(role); // Сохраняем роль в репозиторий

        user = new User();
        user.setName("John Doe");
        user.setPhone("1234567890");
        user.setEmail("john.doe@example.com");
        user.setRole(role); // Устанавливаем роль для пользователя

        // Сохраняем пользователя в репозиторий
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        // Очистка тестовых данных после каждого теста
        userRepository.delete(user);
        roleRepository.delete(role);
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundById() {
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(savedUser);

        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getRole().getId(), savedUser.getRole().getId()); // Проверяем роль
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundByName() {
        User savedUser = userRepository.findByName(user.getName()).orElse(null);
        assertNotNull(savedUser);

        assertEquals(user.getName(), savedUser.getName());
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundByPhone() {
        User savedUser = userRepository.findByPhone(user.getPhone()).orElse(null);
        assertNotNull(savedUser);

        assertEquals(user.getPhone(), savedUser.getPhone());
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundByEmail() {
        User savedUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        assertNotNull(savedUser);

        assertEquals(user.getEmail(), savedUser.getEmail());
    }
}