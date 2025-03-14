package ru.ssau.towp.fluffytailclinic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.models.Role;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;
import ru.ssau.towp.fluffytailclinic.repository.RoleRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Appointment appointment;
    private Animal animal;
    private User vet;

    @BeforeEach
    void setUp() {
        // Создаём роль
        Role role = new Role();
        role.setName("Owner");
        roleRepository.save(role);

        // Создаём владельца животного
        User owner = new User();
        owner.setName("John Doe");
        owner.setRole(role);
        userRepository.save(owner);

        // Создаём животное
        animal = new Animal();
        animal.setName("Buddy");
        animal.setType("Dog");
        animal.setOwner(owner);
        animalRepository.save(animal);

        // Создаём ветеринара
        vet = new User();
        vet.setName("Dr. Smith");
        vet.setRole(role);
        userRepository.save(vet);

        // Создаём запись на приём
        appointment = new Appointment();
        appointment.setAnimal(animal);
        appointment.setVet(vet);
        appointment.setDate(LocalDateTime.now());
        appointmentRepository.save(appointment);
    }

    @AfterEach
    public void tearDown() {
        appointmentRepository.deleteAll();
        animalRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void givenAppointment_whenSaved_thenCanBeFoundById() {
        // Проверяем, что запись найдена
        Optional<Appointment> savedAppointment = appointmentRepository.findById(appointment.getId());
        assertTrue(savedAppointment.isPresent(), "Запись должна быть найдена по ID");

        // Проверяем совпадение данных
        assertEquals(appointment.getId(), savedAppointment.get().getId());
        assertEquals(animal.getId(), savedAppointment.get().getAnimal().getId());
        assertEquals(vet.getId(), savedAppointment.get().getVet().getId());
        assertEquals(appointment.getDate(), savedAppointment.get().getDate());
    }

    @Test
    void whenFindAll_thenReturnAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        assertFalse(appointments.isEmpty(), "Список записей не должен быть пустым");
        assertEquals(1, appointments.size(), "Должна быть только одна запись");
    }

    @Test
    void whenFindByVet_thenReturnAppointments() {
        List<Appointment> vetAppointments = appointmentRepository.findByVet(vet.getId());
        assertFalse(vetAppointments.isEmpty(), "Должны быть записи для данного ветеринара");
        assertEquals(1, vetAppointments.size(), "Должна быть одна запись у ветеринара");
        assertEquals(vet.getId(), vetAppointments.get(0).getVet().getId());
    }



    @Test
    void whenDeleteAppointment_thenShouldNotBeFound() {
        appointmentRepository.delete(appointment);

        Optional<Appointment> deletedAppointment = appointmentRepository.findById(appointment.getId());
        assertFalse(deletedAppointment.isPresent(), "Запись должна быть удалена");
    }
}
