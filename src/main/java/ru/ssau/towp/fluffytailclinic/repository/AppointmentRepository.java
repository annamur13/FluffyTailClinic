package ru.ssau.towp.fluffytailclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.towp.fluffytailclinic.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
