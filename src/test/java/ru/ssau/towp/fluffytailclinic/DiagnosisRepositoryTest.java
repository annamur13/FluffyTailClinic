package ru.ssau.towp.fluffytailclinic;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.towp.fluffytailclinic.models.Diagnosis;
import ru.ssau.towp.fluffytailclinic.repository.DiagnosisRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = {
//        "spring.datasource.url=jdbc:postgresql://localhost:5432/clinic_test",
//        "spring.jpa.hibernate.ddl-auto=create-drop",
//        "spring.datasource.username=postgres",
//        "spring.datasource.password=postgres"
//})
//@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DiagnosisRepositoryTest {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    Diagnosis diagnosis;


    @Test
    void givenDiagnosis_whenSaved_thenCanBeFoundById() {
        Diagnosis savedDiagnosis = diagnosisRepository.findById(diagnosis.getId()).orElse(null);
        Assertions.assertNotNull(savedDiagnosis);

        Assertions.assertEquals(diagnosis.getName(), savedDiagnosis.getName());
        Assertions.assertEquals(diagnosis.getDescription(), savedDiagnosis.getDescription());
    }
    @Test
    void givenDiagnosis_whenSaved_thenCanBeFoundByName() {
        Diagnosis savedDiagnosis = diagnosisRepository.findByName(diagnosis.getName()).orElse(null);
        Assertions.assertNotNull(savedDiagnosis);

        Assertions.assertEquals(diagnosis.getDescription(), savedDiagnosis.getDescription());
    }



    @BeforeEach
    public void setUp() {
        // Initialize test data before each test method
        diagnosis = new Diagnosis("Чумка_test", "Для теста диагноз");

        diagnosisRepository.save(diagnosis);
        /*
        courseRepository.save(course);
        courseRepository.save(course2);

        courses.add(course);
        courses.add(course2);

        student.setLikedCourses(courses);

        studentRepository.save(student);
        studentRepository.save(student2);*/
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method

        diagnosisRepository.delete(diagnosis);

    }
}
