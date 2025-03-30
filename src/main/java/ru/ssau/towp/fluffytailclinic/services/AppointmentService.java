package ru.ssau.towp.fluffytailclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.ssau.towp.fluffytailclinic.controller.NF.ResourceNotFoundException;
import ru.ssau.towp.fluffytailclinic.dto.AppointmentDTO;
import ru.ssau.towp.fluffytailclinic.models.Animal;
import ru.ssau.towp.fluffytailclinic.models.Appointment;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.repository.AnimalRepository;
import ru.ssau.towp.fluffytailclinic.repository.AppointmentRepository;
import ru.ssau.towp.fluffytailclinic.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, AnimalRepository animalRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentRepository.findAll()
                .stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    // Получить приём по ID
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Приём с ID " + id + " не найден"));
        return ResponseEntity.ok(new AppointmentDTO(appointment));
    }

    // Создать новый приём
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Animal animal = animalRepository.findById(appointmentDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Животное с ID " + appointmentDTO.getAnimalId() + " не найдено"));

        User vet = userRepository.findById(appointmentDTO.getVetId())
                .orElseThrow(() -> new ResourceNotFoundException("Ветеринар с ID " + appointmentDTO.getVetId() + " не найден"));

        Appointment appointment = new Appointment();
        appointment.setAnimal(animal);
        appointment.setVet(vet);
        appointment.setDate(appointmentDTO.getDate());
        appointment.setTime(appointmentDTO.getTime());
        appointment.setDescription(appointmentDTO.getDescription());

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentDTO(savedAppointment));
    }

    // Обновить приём
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Приём с ID " + id + " не найден"));

        Animal animal = animalRepository.findById(appointmentDTO.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Животное с ID " + appointmentDTO.getAnimalId() + " не найдено"));

        User vet = userRepository.findById(appointmentDTO.getVetId())
                .orElseThrow(() -> new ResourceNotFoundException("Ветеринар с ID " + appointmentDTO.getVetId() + " не найден"));

        appointment.setAnimal(animal);
        appointment.setVet(vet);
        appointment.setDate(appointmentDTO.getDate());
        appointment.setTime(appointmentDTO.getTime());
        appointment.setDescription(appointmentDTO.getDescription());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return ResponseEntity.ok(new AppointmentDTO(updatedAppointment));
    }

    // Удалить приём
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Приём с ID " + id + " не найден"));

        appointmentRepository.delete(appointment);
        return ResponseEntity.noContent().build();
    }
}