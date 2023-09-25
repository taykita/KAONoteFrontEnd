package ru.kao.kaonotefrontend.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.util.Assert;
import ru.kao.kaonotefrontend.integration.GatewayClient;

import java.util.function.Supplier;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
    @Bean
    @Profile("PROD")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return securityConfigure(http)
                .build();
    }

    @Bean
    @Profile("DEV")
    public SecurityFilterChain securityFilterChainUnsafe(HttpSecurity http) throws Exception {
        return securityConfigure(http)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/**"))
                .build();
    }

    private static HttpSecurity securityConfigure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/signup", "/registration", "/css/**", "static/css/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/login/process").permitAll()
                        .defaultSuccessUrl("/", true).permitAll())
                .logout((logout) -> logout.
                        logoutUrl("/logout").clearAuthentication(true).permitAll()
                        .logoutSuccessUrl("/login").permitAll());
    }

    @Bean
    @Profile("PROD")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("DEV")
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public UserDetailsService userDetailsService(GatewayClient gatewayClient) {
        return new AccountDetailsService(gatewayClient);
    }
}
