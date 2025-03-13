package ru.ssau.towp.fluffytailclinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;

public class AppointmentRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    Appointment appointment;


    @Test
    void givenAppointment_whenSaved_thenCanBeFoundById() {

        Appointment savedAppointment = appointmentRepository.findById(appointment.getId()).orElse(null);
        Assertions.assertNotNull(savedAppointment);

        Assertions.assertEquals(appointment.getId(), savedAppointment.getId());
    }
    @Test
    void givenAppointment_whenSaved_thenCanBeFoundByAnimalId() {

        Appointment savedAppointment = appointmentRepository.findByAnimalId(appointment.getAnimal().getId()).orElse(null);
        Assertions.assertNotNull(savedAppointment);

        Assertions.assertEquals(appointment.getAnimal(), savedAppointment.getAnimal());
    }
    @Test
    void givenAppointment_whenSaved_thenCanBeFoundByVetId() {

        Appointment savedAppointment = appointmentRepository.findByVet(appointment.getVet()).orElse(null);
        Assertions.assertNotNull(savedAppointment);

        Assertions.assertEquals(appointment.getVet(), savedAppointment.getVet());
    }
    @Test
    void givenAppointment_whenSaved_thenCanBeFoundByDate() {

        Appointment savedAppointment = appointmentRepository.findByDate(appointment.getDate()).orElse(null);
        Assertions.assertNotNull(savedAppointment);

        Assertions.assertEquals(appointment.getDate(), savedAppointment.getDate());
    }
}
