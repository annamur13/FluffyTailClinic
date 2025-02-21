package ru.ssau.towp.fluffytailclinic.services;


import ru.ssau.towp.fluffytailclinic.models.User;

public interface UserService {
    public boolean addUser(User user);

    public String encryptPassword(String password);
}
