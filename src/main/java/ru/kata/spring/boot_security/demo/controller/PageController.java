package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.util.SecurityUtil;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("user_auth", principal);
        if (SecurityUtil.isAdmin()) {
            model.addAttribute("isAdmin", true);
            return "admin";
        } else {
            model.addAttribute("isAdmin", false);
            return "user";
        }
    }
}