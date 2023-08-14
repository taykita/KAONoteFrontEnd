package ru.kao.kaonotefrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/test")
    public String testPage() {
        return "main";
    }
}
