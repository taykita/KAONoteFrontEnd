package ru.kao.kaonotefrontend.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kao.kaonotefrontend.configuration.security.AccountDetailsService;
import ru.kao.kaonotefrontend.entity.Account;
import ru.kao.kaonotefrontend.service.AuthService;
import ru.kao.kaonotefrontend.util.LoggerUtil;
import ru.kao.kaonotefrontend.util.SecurityUtil;

@Controller
public class AuthController {
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private static Logger logger = LoggerUtil.getLogger(AuthController.class);

    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        if (SecurityUtil.isNotAnonymous(SecurityContextHolder.getContext())) {
            logger.debug("Account is not anonymous. Redirect");
            return "redirect:/";
        }

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
        logger.debug("Start registration with email = {}", account.getEmail());
        boolean isAccountCreated = authService.createAccount(account);
        if (isAccountCreated) {
            logger.debug("Account is created with email = {}", account.getEmail());
            return "redirect:/login";
        } else {
            logger.debug("Account with email = {} is not created. Redirecting with error.", account.getEmail());
            return "redirect:/registration?error=not-created";
        }
    }
}
