INSERT INTO public.roles
(role_id, "name")
VALUES(0, 'Ветеринар');

INSERT INTO public.roles
(role_id, "name")
VALUES(1, 'Хозяин');

-- пароль 768688
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(0, 'Владелец Барсика и Принцессы', 'vasya@mail.ru', 'Василий Иванов', '$2a$10$JILLKm3fRfoRcT11B3YysucCpekS6e5z.q1yV.95yXtUcePazYaha', 85675674343, 1);

-- пароль 211
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(5, 'Владелец Дружка', 'nikita@mail.ru', 'Никита Алексеев', '$2a$10$cl2ORw5j9X8FrOTDV6yZ0.b07Hcaj9Cpfojussy/tTZvk6A.exn0O', 89212133535, 1);

-- пароль 566
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(6, 'Владелец Кексика', 'egor@mail.ru', 'Егор Дмитриев', '$2a$10$Gtcbzz6w.G595E0N2VkkTeieAt63LHdDCDNs7raAHqY55AYmEEIka', 88213218989, 1);

-- пароль 561
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(7, 'Владелец Кешы', 'agata@mail.ru', 'Агата Мельникова', '$2a$10$VK7CZzIF.6fja6Qon9mLpeNZ6IojGcpNMXx0B2MymdaVbvdGrB94C', 80210982121, 0);

-- пароль 562
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(8, 'Владелец Герды', 'glafira@mail.ru', 'Глафира Ромашкова', '$2a$10$Esh11aPeUPqAtjm838Tc5emu9sQpEZSe9h/wpib2FdHmQvT6CcyyO', 89099092121, 0);

-- пароль 123
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(1, 'Ветеринар-фелинолог', 'katya1996@mail.ru', 'Екатерина Котова', '$2a$10$IwCK3vNVhBbR.K.Lya6..eyKiVHmuQnMqQyIiGbFSG.1CdbHm/aAO', 81231233131, 0);

-- пароль 12345
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(2, 'Ветеринар-кинолог', 'andrey1990@mail.ru', 'Андрей Волков', '$2a$10$JRZYBIU2hr/xOkIUkC71PeJNRbVUbVMhz18GJfAFjII/u.nFhojsW', 89878766565, 0);

-- пароль 123456
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(3, 'Ветеринар-ратолог', 'masha@mail.ru', 'Мария Мышкина', '$2a$10$DKl9vJNdiu4zqXsmOncIh.fV9nOh1CCSm5XPGomtsuNoCI/ecdDzq', 81235676565, 0);

-- пароль 123321
INSERT INTO public.users
(user_id, user_description, user_email, user_name, user_password, user_phone, role_id)
VALUES(4, 'Ветеринар-орнитолог', 'ivan@mail.ru', 'Иван Воробьев', '$2a$10$o8XBiGw0lI3J/tTgt0.b4OLdzxA4GojlgjFJzOjATNahytzzLJwCW', 82343213232, 0);

INSERT INTO public.diagnoses
(diagnosis_id, description, "name")
VALUES(0, 'Вызывает лихорадку, поражает дыхательную, нервную и пищеварительную системы. Передаётся воздушно-капельным путём.', 'Чумка');

INSERT INTO public.diagnoses
(diagnosis_id, description, "name")
VALUES(1, 'Передаётся через воду или пищу, загрязнённые мочой заражённых животных. Вызывает лихорадку, поражение печени и почек, жёлтуху, рвоту.', 'Лептоспироз');

INSERT INTO public.diagnoses
(diagnosis_id, description, "name")
VALUES(2, 'Вирусное заболевание, поражающее дыхательные пути и ротовую полость. Симптомы: язвы на языке, насморк, чихание, высокая температура. Передаётся воздушно-капельным путём или через заражённые предметы.', 'Кальцивироз');

INSERT INTO public.animals
(animal_id, animal_name, "type", owner_id)
VALUES(0, 'Барсик', 'Кошка', 0);

INSERT INTO public.animals
(animal_id, animal_name, "type", owner_id)
VALUES(1, 'Дружок', 'Собака', 5);

INSERT INTO public.animals
(animal_id, animal_name, "type", owner_id)
VALUES(2, 'Принцесса', 'Кошка', 0);

INSERT INTO public.animals
(animal_id, animal_name, "type", owner_id)
VALUES(3, 'Кексик', 'Крыса', 6);

INSERT INTO public.animals
(animal_id, animal_name, "type", owner_id)
VALUES(4, 'Кеша', 'Волнистый попугайчик', 7);

INSERT INTO public.animals
(animal_id, animal_name, "type", owner_id)
VALUES(5, 'Герда', 'Собака', 8);

INSERT INTO public.appointments
(id, "date", description, animal_id, vet_id)
VALUES(0, '05.03.2025', 'Лечение от чумки', 1, 2);

INSERT INTO public.appointments
(id, "date", description, animal_id, vet_id)
VALUES(1, '15.03.2025', 'Лечение от лептоспироза', 1, 1);

INSERT INTO public.appointments
(id, "date", description, animal_id, vet_id)
VALUES(2, '10.03.2025', 'Лечение от кальцивроза', 2, 1);

INSERT INTO public.diagnosis_appointment
(diag_app_id, appointment_id, diagnosis_id)
VALUES(0, 0, 0);

INSERT INTO public.diagnosis_appointment
(diag_app_id, appointment_id, diagnosis_id)
VALUES(1, 1, 1);

INSERT INTO public.diagnosis_appointment
(diag_app_id, appointment_id, diagnosis_id)
VALUES(2, 2, 2);
