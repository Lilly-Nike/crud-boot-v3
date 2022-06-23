package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("user_auth", principal);
        return "users-info";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        model.addAttribute("user_auth", principal);
        return "user";
    }

    @GetMapping("/admin/add")
    public String getAddPage(Model model, Principal principal) {
        model.addAttribute("user_auth", principal);
        model.addAttribute("user", new User());
        return "add_user";
    }
}
