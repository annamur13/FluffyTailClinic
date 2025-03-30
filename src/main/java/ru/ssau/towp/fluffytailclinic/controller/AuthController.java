package ru.ssau.towp.fluffytailclinic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.RoleRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;
import ru.ssau.towp.fluffytailclinic.security.JwtUtil;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login.html";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register.html";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username,
                                               @RequestParam String email,
                                               @RequestParam String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }

        Role userRole = roleRepository.findByName("Хозяин");
        if (userRole == null) {
            return ResponseEntity.badRequest().body("Role not found");
        }

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Хэшируем пароль
        user.setRole(userRole);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
}
