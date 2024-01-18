package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<User> getAllUsers() {
        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, Set<Role> roles) {
        User user = findByUsername(username);
        user.getRoles().clear();
        user.getRoles().addAll(roles);
    }

    @Override
    @Transactional
    public boolean saveUserWithRole(User user, Set<Role> roles, String first_Name, String last_Name, String mail) {

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setFirst_Name(first_Name);
        user.setLast_Name(last_Name);
        user.setMail(mail);
        saveUser(user);
        addRoleToUser(user.getUsername(), roles);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(User userUpdate) {
        if (userRepository.existsByUsernameAndIdNot(userUpdate.getUsername(), userUpdate.getId())) {
            return false;
        }
        User existingUser = userRepository.findById(userUpdate.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(userUpdate.getUsername());
            existingUser.setFirst_Name(userUpdate.getFirst_Name());
            existingUser.setLast_Name(userUpdate.getLast_Name());
            existingUser.setMail(userUpdate.getMail());
            existingUser.setRoles(userUpdate.getRoles());
            userRepository.save(existingUser);
            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }
}
