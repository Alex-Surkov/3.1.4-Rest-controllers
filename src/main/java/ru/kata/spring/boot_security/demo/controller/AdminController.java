package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final PasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public AdminController(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }


    @GetMapping
    public String printUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping
    public String addUser(
            @RequestParam("Name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("age") int age,
            @RequestParam("password") String password,
            ModelMap model) {
        userService.saveUser(name, lastName, age, encoder.encode(password));
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id, ModelMap model) {
        userService.removeUserById(id);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") long id, Model user) {
        user.addAttribute(userService.getUser(id));
        return "update";
    }

    @PostMapping("/update")
    public String update(ModelMap model,
                         @RequestParam("id") long id,
                         @RequestParam("name") String newName,
                         @RequestParam("lastName") String newSurname,
                         @RequestParam("age") int newAge) {
        userService.updateUser(id, newName, newAge, newSurname);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }
}