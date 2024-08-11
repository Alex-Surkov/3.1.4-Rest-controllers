package ru.kata.spring.boot_security.demo.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {


    @PersistenceContext
    private EntityManager manager;


    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUsername(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getAge(),
                userDTO.getPassword());
        if (userDTO.getRoles().contains("ADMIN")) {
            user.assignRole(getRoleByRolename("ROLE_ADMIN"));
        }
        if (userDTO.getRoles().contains("USER")) {
            user.assignRole(getRoleByRolename("ROLE_USER"));
        }
        manager.persist(user);
    }
    public void saveRole(Role role) {
        manager.persist(role);
    }

    @Override
    public void assignRoleToUser(User user, Role role) {
        user.assignRole(role);
        manager.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        TypedQuery<User> typedQuery = manager.createQuery("select u from User u where u.id = :id", User.class);
        typedQuery.setParameter("id", id);
        manager.remove(typedQuery.getResultList().stream().findAny().orElse(null));
    }

    @Override
    public List<User> getAllUsers() {
        return manager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(long id) {
        TypedQuery<User> typedQuery = manager.createQuery("select u from User u where u.id = :id", User.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Query("Select u from User u left join fetch u.roles where u.username=:email")
    public User findByUsername(String email) {
        TypedQuery<User> typedQuery = manager.createQuery("select u from User u where u.username = :email", User.class);
        typedQuery.setParameter("email", email);
        return typedQuery.getSingleResult();
    }


    public Role getRoleByRolename(String authority) {
        TypedQuery<Role> typedQuery = manager.createQuery("select r from Role r where r.authority = :authority", Role.class);
        typedQuery.setParameter("authority", authority);
        return typedQuery.getSingleResult();
    }

    @Override
    public void updateUser(long id, UserDTO user) {
        TypedQuery<User> typedQuery = manager.createQuery("select u from User u where u.id = :id", User.class);
        User updatedUser = typedQuery.setParameter("id", id).getSingleResult();
        updatedUser.setUsername(user.getUsername());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setAge(user.getAge());
        updatedUser.setRoles(new ArrayList<Role>());
        if (user.getRoles().contains("ADMIN")) {
            updatedUser.assignRole(getRoleByRolename("ROLE_ADMIN"));
        }
        if (user.getRoles().contains("USER")) {
            updatedUser.assignRole(getRoleByRolename("ROLE_USER"));
        }
    }

}
