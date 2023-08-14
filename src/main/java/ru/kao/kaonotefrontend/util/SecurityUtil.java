package ru.kao.kaonotefrontend.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;

public class SecurityUtil {
    public static boolean isNotAnonymous(SecurityContext context) {
        return context.getAuthentication() != null &&
                context.getAuthentication().isAuthenticated() &&
                !(context.getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
