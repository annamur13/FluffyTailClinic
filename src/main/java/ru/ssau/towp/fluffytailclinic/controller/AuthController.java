package ru.ssau.towp.fluffytailclinic.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.RoleRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password) {

        Role userRole = roleRepository.findByName("Хозяин");

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Хэшируем пароль
        user.setRole(userRole);

        userRepository.save(user);
        return "redirect:/entry"; // Перенаправление на страницу входа
    }

    @GetMapping("/entry")
    public String loginPage() {
        return "entry.html";  // Отображение страницы входа (entry.html)
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration.html";  // Отображение страницы регистрации (registration.html)
    }
}

