package ru.ssau.towp.fluffytailclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.repository.RoleRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            /*System.out.println("Создаём роли ADMIN и USER...");

            Role adminRole = new Role("ADMIN");
            Role userRole = new Role("USER");

            roleRepository.save(adminRole);
            roleRepository.save(userRole);*/
        }
    }
}
