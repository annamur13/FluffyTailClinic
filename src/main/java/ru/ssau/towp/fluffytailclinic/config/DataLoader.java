package ru.ssau.towp.fluffytailclinic.config;

import ru.ssau.towp.fluffytailclinic.models.*;
import ru.ssau.towp.fluffytailclinic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DataLoader { // implements CommandLineRunner

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DiagnosisAppointmentRepository diagnosisAppointmentRepository;

    //@Override
    public void run(String... args) throws Exception {
        // Очистка таблиц (опционально)
        diagnosisAppointmentRepository.deleteAll();
        appointmentRepository.deleteAll();
        diagnosisRepository.deleteAll();
        animalRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Вставка ролей
        Role vetRole = new Role();
        vetRole.setName("Ветеринар");
        roleRepository.save(vetRole);

        Role ownerRole = new Role();
        ownerRole.setName("Хозяин");
        roleRepository.save(ownerRole);

        // Вставка диагнозов
        Diagnosis plague = new Diagnosis();
        plague.setName("Чумка");
        plague.setDescription("Вызывает лихорадку, поражает дыхательную, нервную и пищеварительную системы. Передаётся воздушно-капельным путём.");
        diagnosisRepository.save(plague);

        Diagnosis leptospirosis = new Diagnosis();
        leptospirosis.setName("Лептоспироз");
        leptospirosis.setDescription("Передаётся через воду или пищу, загрязнённые мочой заражённых животных. Вызывает лихорадку, поражение печени и почек, жёлтуху, рвоту.");
        diagnosisRepository.save(leptospirosis);

        Diagnosis calcivirus = new Diagnosis();
        calcivirus.setName("Кальцивироз");
        calcivirus.setDescription("Вирусное заболевание, поражающее дыхательные пути и ротовую полость. Симптомы: язвы на языке, насморк, чихание, высокая температура. Передаётся воздушно-капельным путём или через заражённые предметы.");
        diagnosisRepository.save(calcivirus);

        // Вставка пользователей
        User vasya = new User();
        vasya.setName("Василий Иванов");
        vasya.setEmail("vasya@mail.ru");
        vasya.setPassword("$2a$10$JILLKm3fRfoRcT11B3YysucCpekS6e5z.q1yV.95yXtUcePazYaha");
        vasya.setPhone("85675674343");
        vasya.setRole(ownerRole);
        userRepository.save(vasya);

        User katya = new User();
        katya.setName("Екатерина Котова");
        katya.setEmail("katya1996@mail.ru");
        katya.setPassword("$2a$10$IwCK3vNVhBbR.K.Lya6..eyKiVHmuQnMqQyIiGbFSG.1CdbHm/aAO");
        katya.setPhone("81231233131");
        katya.setRole(vetRole);
        userRepository.save(katya);

        User andrey = new User();
        andrey.setName("Андрей Волков");
        andrey.setEmail("andrey1990@mail.ru");
        andrey.setPassword("$2a$10$JRZYBIU2hr/xOkIUkC71PeJNRbVUbVMhz18GJfAFjII/u.nFhojsW");
        andrey.setPhone("89878766565");
        andrey.setRole(vetRole);
        userRepository.save(andrey);

        User masha = new User();
        masha.setName("Мария Мышкина");
        masha.setEmail("masha@mail.ru");
        masha.setPassword("$2a$10$DKl9vJNdiu4zqXsmOncIh.fV9nOh1CCSm5XPGomtsuNoCI/ecdDzq");
        masha.setPhone("81235676565");
        masha.setRole(vetRole);
        userRepository.save(masha);

        User ivan = new User();
        ivan.setName("Иван Воробьев");
        ivan.setEmail("ivan@mail.ru");
        ivan.setPassword("$2a$10$o8XBiGw0lI3J/tTgt0.b4OLdzxA4GojlgjFJzOjATNahytzzLJwCW");
        ivan.setPhone("82343213232");
        ivan.setRole(vetRole);
        userRepository.save(ivan);

        User nikita = new User();
        nikita.setName("Никита Алексеев");
        nikita.setEmail("nikita@mail.ru");
        nikita.setPassword("$2a$10$cl2ORw5j9X8FrOTDV6yZ0.b07Hcaj9Cpfojussy/tTZvk6A.exn0O");
        nikita.setPhone("89212133535");
        nikita.setRole(ownerRole);
        userRepository.save(nikita);

        User egor = new User();
        egor.setName("Егор Дмитриев");
        egor.setEmail("egor@mail.ru");
        egor.setPassword("$2a$10$Gtcbzz6w.G595E0N2VkkTeieAt63LHdDCDNs7raAHqY55AYmEEIka");
        egor.setPhone("88213218989");
        egor.setRole(ownerRole);
        userRepository.save(egor);

        User agata = new User();
        agata.setName("Агата Мельникова");
        agata.setEmail("agata@mail.ru");
        agata.setPassword("$2a$10$VK7CZzIF.6fja6Qon9mLpeNZ6IojGcpNMXx0B2MymdaVbvdGrB94C");
        agata.setPhone("80210982121");
        agata.setRole(vetRole);
        userRepository.save(agata);

        User glafira = new User();
        glafira.setName("Глафира Ромашкова");
        glafira.setEmail("glafira@mail.ru");
        glafira.setPassword("$2a$10$Esh11aPeUPqAtjm838Tc5emu9sQpEZSe9h/wpib2FdHmQvT6CcyyO");
        glafira.setPhone("89099092121");
        glafira.setRole(vetRole);
        userRepository.save(glafira);

        // Вставка животных
        Animal barsik = new Animal();
        barsik.setName("Барсик");
        barsik.setType("Кошка");
        barsik.setId(vasya.getId());
        animalRepository.save(barsik);

        Animal druzhok = new Animal();
        druzhok.setName("Дружок");
        druzhok.setType("Собака");
        druzhok.setId(nikita.getId());
        animalRepository.save(druzhok);

        Animal princess = new Animal();
        princess.setName("Принцесса");
        princess.setType("Кошка");
        princess.setId(vasya.getId());
        animalRepository.save(princess);

        Animal keksik = new Animal();
        keksik.setName("Кексик");
        keksik.setType("Крыса");
        keksik.setId(egor.getId());
        animalRepository.save(keksik);

        Animal kesha = new Animal();
        kesha.setName("Кеша");
        kesha.setType("Волнистый попугайчик");
        kesha.setId(agata.getId());
        animalRepository.save(kesha);

        Animal gerda = new Animal();
        gerda.setName("Герда");
        gerda.setType("Собака");
        gerda.setId(glafira.getId());
        animalRepository.save(gerda);

        // Вставка назначений
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Appointment appointment1 = new Appointment();
        appointment1.setDate(LocalDate.parse("05.03.2025", formatter).atStartOfDay());
        appointment1.setDescription("Лечение от чумки");
        appointment1.setAnimal(druzhok);
        appointment1.setVet(andrey);
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setDate(LocalDate.parse("15.03.2025", formatter).atStartOfDay());
        appointment2.setDescription("Лечение от лептоспироза");
        appointment2.setAnimal(druzhok);
        appointment2.setVet(katya);
        appointmentRepository.save(appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setDate(LocalDate.parse("10.03.2025", formatter).atStartOfDay());
        appointment3.setDescription("Лечение от кальцивроза");
        appointment3.setAnimal(princess);
        appointment3.setVet(katya);
        appointmentRepository.save(appointment3);

        // Вставка связи диагноза и назначения
        DiagnosisAppointment diagApp1 = new DiagnosisAppointment();
        diagApp1.setAppointment(appointment1);
        diagApp1.setDiagnosis(plague);
        diagnosisAppointmentRepository.save(diagApp1);

        DiagnosisAppointment diagApp2 = new DiagnosisAppointment();
        diagApp2.setAppointment(appointment2);
        diagApp2.setDiagnosis(leptospirosis);
        diagnosisAppointmentRepository.save(diagApp2);

        DiagnosisAppointment diagApp3 = new DiagnosisAppointment();
        diagApp3.setAppointment(appointment3);
        diagApp3.setDiagnosis(calcivirus);
        diagnosisAppointmentRepository.save(diagApp3);
    }
}