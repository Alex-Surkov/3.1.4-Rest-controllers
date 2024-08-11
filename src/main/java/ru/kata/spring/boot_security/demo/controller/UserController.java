package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping
    public String getGreetings(@AuthenticationPrincipal User userDetails, Model model) {
        model.addAttribute(userDetails);
        return "user";
    }
}
