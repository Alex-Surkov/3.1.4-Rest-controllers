package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.dao.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDTO userDTO);

    void removeUserById(long id);

    List<User> getAllUsers();

    User getUser(long id);

    void updateUser(long id, UserDTO user);

    void saveRole(Role role);

    void assignRoleToUser(User user, Role role);
}
