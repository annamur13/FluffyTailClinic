package ru.ssau.towp.fluffytailclinic.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from users as c where c.id = :id",
            nativeQuery = true)
    Optional<User> findById(@Param("id") String id);

    @Query(value = "select * from users as c where c.email = :email",
            nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "select * from users as c where c.name = :name",
            nativeQuery = true)
    Optional<User> findByName(@Param("name") String name);

    @Query(value = "select * from users as c where c.phone = :phone",
            nativeQuery = true)
    Optional<User> findByPhone(@Param("phone") String name);
}
