package ru.ssau.towp.fluffytailclinic.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="user_id")
    private Long id;

    @Column(name="user_name")
    private String name;

    @Column(name="user_password")
    private String password;

    @Column(name="user_phone")
    private String phone;

    @Column(name="user_email")
    private String email;

    @Column(name="user_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Animal> animals;

    public User(){}

}

