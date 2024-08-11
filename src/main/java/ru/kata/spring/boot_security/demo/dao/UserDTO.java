package ru.kata.spring.boot_security.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(String username, String firstName, String lastName, Integer age, List<String> roles, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.roles = roles;
        this.password = password;
    }

    private String password;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return Objects.requireNonNullElseGet(roles, ArrayList::new);
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}