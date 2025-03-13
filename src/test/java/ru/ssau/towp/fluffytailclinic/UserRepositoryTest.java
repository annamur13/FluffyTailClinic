package ru.ssau.towp.fluffytailclinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;


    @Test
    void givenUser_whenSaved_thenCanBeFoundById() {
        User savedUser = userRepository.findById(user.getId()).orElse(null);
        Assertions.assertNotNull(savedUser);

        Assertions.assertEquals(user.getName(), savedUser.getName());
        Assertions.assertEquals(user.getId(), savedUser.getId());
    }
    @Test
    void givenUser_whenSaved_thenCanBeFoundByName() {
        User savedUser = userRepository.findByName(user.getName()).orElse(null);
        Assertions.assertNotNull(savedUser);

    }
    @Test
    void givenUser_whenSaved_thenCanBeFoundByPhone() {
        User savedUser = userRepository.findByPhone(user.getPhone()).orElse(null);
        Assertions.assertNotNull(savedUser);

        Assertions.assertEquals(user.getName(), savedUser.getPhone());
    }
    @Test
    void givenUser_whenSaved_thenCanBeFoundByEmail() {
        User savedUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        Assertions.assertNotNull(savedUser);

        Assertions.assertEquals(user.getName(), savedUser.getEmail());
    }

}
