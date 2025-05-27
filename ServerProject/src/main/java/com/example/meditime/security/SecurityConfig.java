package com.example.meditime.security;

import com.example.meditime.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    @Order(1)
    public SecurityFilterChain webFilterChain(HttpSecurity http,
                                              CustomLoginSuccessHandler customLoginSuccessHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/download", "/css/**", "/js/**","/mobile/**",
                                "/faq",
                                "/api/medication/get/{carerId}",
                                "/logs/get/{id}",
                                "logs/get/ai/{patientId}",
                                "/logs/post/log",
                                "/meds/details",
                                "/api/**"
                                ).permitAll()
                        .requestMatchers("/admin/**", "/dashboard").hasRole("ADMIN")
                        .requestMatchers("/admin/medications/").hasRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customLoginSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/mobile/secureData", "/mobile/test-email", "/mobile/is_verified", "/mobile/verify",
                                "/mobile/login", "/mobile/removeCarerFromClient",
                                "/api/medication/clients-with-medications/{carerId}",
                                "/api/medication/schedule/{clientId}", "api/medication/names/{clientId}",
                                "/mobile/**", "/mobile/userCarer", "mobile/check",
                                "api/clients", "api/medications", "api/medication/assign", "/meds/**", "/login/**", "/meds/details",
                                "/api/auth/**", "/mobile/user", "/signup", "/download", "/css/**", "/js/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}