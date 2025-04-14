package ru.ssau.towp.fluffytailclinic.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;
import ru.ssau.towp.fluffytailclinic.services.UserService;


@Service
public class UserServiceImpl extends UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(AppointmentRepository appointmentRepository, AnimalRepository animalRepository, UserRepository userRepository) {
        super(appointmentRepository, animalRepository, userRepository);
    }

    @Override
    public boolean addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return false;
        user.setPassword(encryptPassword(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public String encryptPassword(String password){ return passwordEncoder.encode(password); }
}
