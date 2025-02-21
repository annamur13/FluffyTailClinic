package ru.ssau.towp.fluffytailclinic.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.towp.fluffytailclinic.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
