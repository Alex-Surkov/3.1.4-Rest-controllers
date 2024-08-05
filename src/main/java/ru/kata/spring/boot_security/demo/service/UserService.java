package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void saveUser(String name, String lastName, int age, String password);

    void removeUserById(long id);

    List<User> getAllUsers();

    User getUser(long id);

    void updateUser(long id, String newName, int newAge, String newSurname);
}
