package ru.ssau.towp.fluffytailclinic.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animals")
@AllArgsConstructor
@Getter
@Setter

public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="animal_id")
    private Long id;
    @Column(name = "animal_name")
    private String name;
    @Column(name="type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Animal(){}

 }

