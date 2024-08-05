package ru.kata.spring.boot_security.demo.service;


import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserDao dao;


    @Autowired
    public UserServiceImpl(PasswordEncoder encoder, UserDao dao, User user) {
        this.encoder = encoder;
        this.dao = dao;
    }


    @Transactional
    public void saveUser(String name, String lastName, int age, String password) throws HibernateException {
        User user = new User(name, lastName, age);
        user.setPassword(encoder.encode(password));
        dao.saveUser(user);
    }

    @Transactional
    public void removeUserById(long id) throws HibernateException {
        dao.removeUserById(id);
    }

    @Transactional
    public List<User> getAllUsers() throws HibernateException {
        return dao.getAllUsers();
    }


    @Transactional
    @Override
    public User getUser(long id) {
        return dao.getUser(id);
    }

    @Transactional
    @Override
    public void updateUser(long id, String newName, int newAge, String newSurname) {
        dao.updateUser(id, newName, newAge, newSurname);
    }

}
