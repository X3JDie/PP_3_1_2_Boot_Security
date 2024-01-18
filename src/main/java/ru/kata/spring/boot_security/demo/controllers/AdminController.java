package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    private String redirectToLoginPage() {
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String HomePage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String AllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "all-users";
    }

    @RequestMapping("/admin/addUser")
    public String AddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "add-user";
    }

    @PostMapping(value = "/saveUser")
    private String saveUser(@ModelAttribute("user") User user,
                            @RequestParam("roles") Set<Role> roles,
                            @RequestParam("first_Name") String first_Name,
                            @RequestParam("last_Name") String last_Name,
                            @RequestParam("mail") String mail) {
        if (userService.saveUserWithRole(user, roles, first_Name, last_Name, mail)) {
            return "redirect:/admin";
        } else {
            return "error-username";
        }
    }

    @PostMapping(value = "/updateUser")
    private String updateUser(@ModelAttribute("user") User user) {
        if (userService.updateUser(user)) {
            return "redirect:/admin";
        } else {
            return "error-username";
        }
    }

    @RequestMapping("/admin/updateInfo")
    public String editUserForm(@RequestParam("userId") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("roleListToUser", new HashSet<>());
        return "edit-user";
    }

    @RequestMapping("/admin/deleteUser")
    private String deleteUser(@RequestParam("userId") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}