package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;

@RestController
@RequestMapping("api/user")
public class UserRestController {

    @GetMapping
    public User getUserDetails(@AuthenticationPrincipal User userDetails) {
        // Return the authenticated user's details as JSON
        return userDetails;
    }
}
