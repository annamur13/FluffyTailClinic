package ru.ssau.towp.fluffytailclinic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ssau.towp.fluffytailclinic.models.*;
import ru.ssau.towp.fluffytailclinic.repository.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FluffytailclinicApplicationTests {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private DiagnosisAppointmentRepository diagnosisAppointmentRepository;

	@Test
	void testAppointmentWithAnimalAndDiagnosis() {
		// Получаем запись на прием по ID (например, 0)
		Appointment appointment = appointmentRepository.findById(0L).orElse(null);
		assertNotNull(appointment, "Запись на прием не найдена");

		// Получаем животное, связанное с записью на прием
		Animal animal = (Animal) appointment.getAnimal(); // Приведение типа
		assertNotNull(animal, "Животное не найдено");

		// Получаем диагнозы, связанные с записью на прием
		List<DiagnosisAppointment> diagnosisAppointments = diagnosisAppointmentRepository.findByAppointment(appointment);
		assertNotNull(diagnosisAppointments, "Диагнозы не найдены");

		// Выводим информацию
		System.out.println("Запись на прием: " + appointment.getDescription());
		System.out.println("Животное: " + animal.getName() + " (" + animal.getType() + ")");
		System.out.println("Диагнозы:");
		for (DiagnosisAppointment da : diagnosisAppointments) {
			Diagnosis diagnosis = (Diagnosis) da.getDiagnosis(); // Приведение типа
			System.out.println("- " + diagnosis.getName() + ": " + diagnosis.getDescription());
		}
	}
}
