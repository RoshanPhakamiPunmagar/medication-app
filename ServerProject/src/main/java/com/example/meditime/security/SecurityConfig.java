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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    // 🟢 Web Session-Based Login
    @Bean
    @Order(1)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection for simplicity (adjust if needed for production)
                .csrf(csrf -> csrf.disable())

                // Set the custom UserDetailsService for authentication
                .userDetailsService(userDetailsService)

                // Configure URL authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow unauthenticated access to these URLs (login, signup, static resources)
                        .requestMatchers("/login", "/signup", "/download", "/css/**", "/js/**").permitAll()

                        // Only users with 'ADMIN' role can access URLs under /admin/
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Only users with 'CARER' role can access URLs under /support/
                        .requestMatchers("/support/**").hasRole("CARER")

                        // Only users with 'CLIENT' role can access URLs under /client/
                        .requestMatchers("/client/**").hasRole("CLIENT")

                        // All other requests require the user to be authenticated (logged in)
                        .anyRequest().authenticated()
                )

                // Configure form-based login
                .formLogin(form -> form
                        // Custom login page URL
                        .loginPage("/login")

                        // Redirect here after successful login (true forces redirect even if user was trying to access other page)
                        .defaultSuccessUrl("/dashboard", true)

                        // Allow everyone to access login page
                        .permitAll()
                )

                // Configure logout behavior
                .logout(logout -> logout
                        // URL to trigger logout (default is /logout)
                        .logoutUrl("/logout")

                        // Redirect here after successful logout
                        .logoutSuccessUrl("/login?logout")

                        // Allow everyone to access logout URL
                        .permitAll()
                )

                // Session management configuration
                .sessionManagement(session -> session
                        // Create session if required (enable sessions for web login)
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        // Build and return the configured SecurityFilterChain
        return http.build();
    }


    // 🟢 Mobile JWT-Based Login
    @Bean
    @Order(2) // LOWER priority
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/mobile/**",
                                "/api/**",
                                "/login/**", "/signup", "/download", "/css/**", "/js/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

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
