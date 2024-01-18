package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "First_Name")
    private String First_Name;

    @Column(name = "Last_Name")
    private String Last_Name;

    @Column(name = "Mail")
    private String Mail;


    @ManyToMany(cascade = {CascadeType.PERSIST
            , CascadeType.MERGE
            , CascadeType.DETACH
            , CascadeType.REFRESH}
            , fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, Set<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    public User(String username, String password, Set<Role> roles, String First_Name, String Last_Name, String Mail) {
        this.username = username;
        this.roles = roles;
        this.password = password;
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Mail = Mail;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirst_Name(String first_Name) {
        this.First_Name = first_Name;
    }

    public void setLast_Name(String last_Name) {
        this.Last_Name = last_Name;
    }

    public void setMail(String mail) {
        this.Mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public String getMail() {
        return Mail;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
