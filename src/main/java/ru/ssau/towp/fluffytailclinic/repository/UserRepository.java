package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ssau.towp.fluffytailclinic.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Используем правильные имена столбцов из таблицы users
    @Query(value = "SELECT * FROM users WHERE user_id = :id", nativeQuery = true)
    Optional<User> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM users WHERE user_email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE user_name = :name", nativeQuery = true)
    Optional<User> findByName(@Param("name") String name);

    @Query(value = "SELECT * FROM users WHERE user_phone = :phone", nativeQuery = true)
    Optional<User> findByPhone(@Param("phone") String phone);

    @Query(value = "SELECT * FROM users WHERE role_id = :role", nativeQuery = true)
    List<User> findByRole(@Param("role") Integer role);
}