package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.towp.fluffytailclinic.models.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
