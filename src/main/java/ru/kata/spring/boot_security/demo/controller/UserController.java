package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserServiceImpl userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }


    // Страница user.
    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        model.addAttribute("user", user);
        return "user";
    }

    // страница админа с функциональными возможностями
    @GetMapping("/admin")
    public String adminInfo(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    // создание нового юзера
    @GetMapping("/users-create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "user-create";
    }


    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles) {
        user.setRoles(userService.getRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/";
    }


    // Изменение существующего юзера
    @GetMapping("/admin/user-update/{id}")
    public String getUserById(@PathVariable("id") Integer id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }



    //Удаление нафиг
    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

}
