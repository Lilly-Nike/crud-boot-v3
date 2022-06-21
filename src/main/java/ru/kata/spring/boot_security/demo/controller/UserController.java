package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user_auth", principal);
        model.addAttribute("roles", roleService.findAll());
        return "users";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        model.addAttribute("user_auth", principal);
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "user";
    }

    @GetMapping("/admin/add")
    public String getAddPage(Model model, Principal principal) {
        model.addAttribute("user_auth", principal);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/add")
    public String saveUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
