package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserService userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // Create default users
        Role user = new Role("ROLE_USER");
        Role adm = new Role("ROLE_ADMIN");
        userRepository.saveRole(user);
        userRepository.saveRole(adm);
        User user1 = new User("user", "user", 30);
        user1.setPassword(passwordEncoder.encode("user"));
        User user2 = new User("admin", "admin", 30);
        user2.setPassword(passwordEncoder.encode("admin"));
        user2.assignRole(adm);
        // Save users to database
        userRepository.saveUser(user1);
        userRepository.saveUser(user2);
    }
}