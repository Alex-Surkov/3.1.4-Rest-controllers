package ru.kata.spring.boot_security.demo.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {


    @PersistenceContext
    private EntityManager manager;


    @Override
    public void saveUser(User user) {
        user.assignRole(getRoleByRolename("ROLE_USER"));
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

    @Query("Select u from User u left join fetch u.roles where u.userName=:name")
    public User findByUsername(String name) {
        TypedQuery<User> typedQuery = manager.createQuery("select u from User u where u.userName = :name", User.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getSingleResult();
    }


    public Role getRoleByRolename(String authority) {
        TypedQuery<Role> typedQuery = manager.createQuery("select r from Role r where r.authority = :authority", Role.class);
        typedQuery.setParameter("authority", authority);
        return typedQuery.getSingleResult();
    }


    @Override
    public void updateUser(long id, String newName, int newAge, String newSurname) {
        TypedQuery<User> typedQuery = manager.createQuery("select u from User u where u.id = :id", User.class);
        User user = typedQuery.setParameter("id", id).getSingleResult();
        user.setUserName(newName);
        user.setLastName(newSurname);
        user.setAge(newAge);
    }

}
