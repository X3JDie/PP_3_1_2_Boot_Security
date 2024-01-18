package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

@Service
public interface UserService {

    Set<User> getAllUsers();

    void saveUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);

    User findByUsername(String username);

    Set<Role> getAllRoles();


    void addRoleToUser(String username, Set<Role> role);

    boolean saveUserWithRole(User user, Set<Role> role, String first_Name, String last_Name, String mail);

    boolean updateUser(User userUpdate);
}
