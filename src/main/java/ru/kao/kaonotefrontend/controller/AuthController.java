package ru.kao.kaonotefrontend.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kao.kaonotefrontend.util.SecurityUtil;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        if (SecurityUtil.isNotAnonymous(SecurityContextHolder.getContext()))
            return "redirect:/";

        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
