package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id") // Первичный ключ
    private Long id;

    @Column(nullable = false, unique = true) // Уникальное имя роли (ADMIN, USER и т. д.)
    private String name;

    // ❗ Конструктор без параметров (нужен Hibernate)
    public Role() {}

    // ✅ Конструктор с параметром (удобно для создания ролей)
    public Role(String name) {
        this.name = name;
    }

    // ✅ Метод Spring Security, который возвращает имя роли
    @Override
    public String getAuthority() {
        return name;
    }

    // ✅ Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

