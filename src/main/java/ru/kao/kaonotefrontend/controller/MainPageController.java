package ru.kao.kaonotefrontend.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kao.kaonotefrontend.util.SecurityUtil;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String mainPage(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("isAuthenticated",
                SecurityUtil.isNotAnonymous(context));
        return "main";
    }
}
