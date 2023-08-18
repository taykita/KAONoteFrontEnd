package ru.kao.kaonotefrontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kao.kaonotefrontend.entity.Account;
import ru.kao.kaonotefrontend.service.AuthService;
import ru.kao.kaonotefrontend.util.SecurityUtil;

@Controller
public class AuthController {
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        if (SecurityUtil.isNotAnonymous(SecurityContextHolder.getContext()))
            return "redirect:/";

        return "login";
    }

    /**
     * Logout endpoint
     * @return redirect to root
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    /**
     * Registration page
     * @return html file name
     */
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "registration";
    }

    /**
     * Registration endpoint
     * @param account account for registration
     * @return redirect to login
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute("account") Account account) throws JSONException {
        boolean isAccountCreated = authService.createAccount(account);
        if (isAccountCreated)
            return "redirect:/login";
        else
            return "redirect:/registration?error=not-created";
    }
}
