package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

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
        UserDTO user1 = new UserDTO("user@gmail.com", "user", "user", 30, List.of("USER"), "user");
        user1.setPassword(passwordEncoder.encode("user"));
        UserDTO user2 = new UserDTO("admin@gmail.com","admin", "admin", 30, List.of("ADMIN"), "admin");
        user2.setPassword(passwordEncoder.encode("admin"));
        // Save users to database
        userRepository.saveUser(user1);
        userRepository.saveUser(user2);
    }
}