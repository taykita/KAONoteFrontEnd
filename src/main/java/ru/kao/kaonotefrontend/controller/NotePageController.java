package ru.kao.kaonotefrontend.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kao.kaonotefrontend.util.SecurityUtil;

@Controller
@RequestMapping("/note")
public class NotePageController {
    @GetMapping("/main")
    public String mainNotePage(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("isAuthenticated",
                SecurityUtil.isNotAnonymous(context));
        return "main-note";
    }

    @GetMapping("/create")
    public String createNotePage(Model model) {
        return "create-note";
    }

    @PostMapping("/create")
    public void createNote() {

    }

}
