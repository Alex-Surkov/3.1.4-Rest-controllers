package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kata.spring.boot_security.demo.dao.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminRestController {

    private final PasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public AdminRestController(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @ModelAttribute("currentUser")
    public UserDTO currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return new UserDTO(user.getUsername(), user.getFirstName(), user.getLastName(), user.getAge(), user.getRolenames(), user.getPassword());
    }

    @GetMapping("/admin")
    public String printUsers() {
        return "admin";
    }

    @GetMapping("/api/admin/users")
    public @ResponseBody ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setAge(user.getAge());
            List<String> roleNames = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList());
            dto.setRoles(roleNames);
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/api/admin/users")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userService.saveUser(userDTO);
        return ResponseEntity.ok(userService.getUser(userDTO.getId()));
    }

    @DeleteMapping("/api/admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {

        userService.removeUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/admin/users/{id}")
    public @ResponseBody ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/api/admin/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserDTO userDTO) {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok(userService.getUser(id));
    }
}
