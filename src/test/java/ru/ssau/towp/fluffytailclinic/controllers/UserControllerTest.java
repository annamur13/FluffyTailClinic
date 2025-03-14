package ru.ssau.towp.fluffytailclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ssau.towp.fluffytailclinic.controller.UserController;
import ru.ssau.towp.fluffytailclinic.models.User;
import ru.ssau.towp.fluffytailclinic.services.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = UserController.class, secure = false) // Тестируем UserController
@WebMvcTest(value = UserController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService; // Используем UserService

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFetchAllUsers() throws Exception {
        // Мокируем вызов сервиса

        List<User> userList = createUsers();
        given(userService.getAllUsers()).willReturn(userList);

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/users"))
               .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(userList.size())));
    }

    private List<User> createUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPhone("1234567890");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("user2");
        user2.setEmail("user2@gmail.com");
        user2.setPhone("0987654321");

        User user3 = new User();
        user3.setId(3L);
        user3.setName("user3");
        user3.setEmail("user3@gmail.com");
        user3.setPhone("5555555555");

        return Arrays.asList(user1, user2, user3);
    }

    @Test
    void shouldFindUserById() throws Exception {

        User user = new User();
        user.setId(Long.MAX_VALUE);
        user.setName("user1");
        user.setEmail("user1@gmail.com");
        user.setPhone("1234567890");

        // Мокируем вызов сервиса
        given(userService.getUserById(user.getId())).willReturn(Optional.of(user));

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    void shouldFindUserByEmail() throws Exception {
        String userEmail = "user1@gmail.com";
        User user = new User();
        user.setId(Long.MAX_VALUE);
        user.setName("user1");
        user.setEmail(userEmail);
        user.setPhone("1234567890");

        // Мокируем вызов сервиса
        given(userService.getUserByEmail(userEmail)).willReturn(Optional.of(user));

        // Выполняем запрос и проверяем результат
        this.mockMvc.perform(get("/api/users/email/{email}", userEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

//    @Test
//    void shouldFindUserByName() throws Exception {
//        String userName = "user1";
//        User user = new User();
//
//        user.setName(userName);
//        user.setEmail("user1@gmail.com");
//        user.setPhone("1234567890");
//
//        // Мокируем вызов сервиса
//        given(userService.getUserByName(userName)).willReturn(Optional.of(user));
//
//        // Выполняем запрос и проверяем результат
//        this.mockMvc.perform(get("/api/users/name/{name}", userName))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.email").value(user.getEmail()))
//                .andExpect(jsonPath("$.phone").value(user.getPhone()));
//    }

//    @Test
//    void shouldFindUserByPhone() throws Exception {
//        String userPhone = "1234567890";
//        User user = new User();
//
//        user.setName("user1");
//        user.setEmail("user1@gmail.com");
//        user.setPhone(userPhone);
//
//        // Мокируем вызов сервиса
//        given(userService.getUserByPhone(userPhone)).willReturn(Optional.of(user));
//
//        // Выполняем запрос и проверяем результат
//        this.mockMvc.perform(get("/api/users/phone/{phone}", userPhone))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.email").value(user.getEmail()))
//                .andExpect(jsonPath("$.phone").value(user.getPhone()));
//    }
}