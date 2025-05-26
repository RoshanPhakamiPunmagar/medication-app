//package com.example.meditime.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthFilter;
//
//    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
//        this.jwtAuthFilter = jwtAuthFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/mobile/secureData","/mobile/test-email","/mobile/is_verified",
//                        "/mobile/verify","/mobile/login","/mobile/removeCarerFromClient",
//                        "/api/medication/clients-with-medications/{carerId}","/meds/**",
//                        "/api/medication/schedule/{clientId}", "api/medication/names/{clientId}",
//                        "/mobile/**","/mobile/userCarer","mobile/check","api/clients","logs/**",
//                        "api/medications","api/medication/assign",
//                        "/api/medication/**", "/api/medications/import-openfda","/meds/**", "/login/**","/meds/details/**",
//                        "/api/auth/**","/mobile/user", "/signup", "/download", "/css/**", "/js/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}





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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomLoginSuccessHandler customLoginSuccessHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🌐 Web login & static
                        .requestMatchers("/login", "/signup", "/css/**", "/js/**").permitAll()

                        // 📱 Mobile endpoints
                        .requestMatchers("/mobile/**").permitAll()

                        // 🛑 Admin & dashboard still need authentication
                        .requestMatchers("/admin/**", "/dashboard").hasRole("ADMIN")

                        .anyRequest().denyAll()
                )
//        http
//                .csrf(csrf -> csrf.disable()) // Consider enabling with CSRF token if needed
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/login", "/signup", "/download", "/css/**", "/js/**"
//                                // 📱 Mobile endpoints
//                        .requestMatchers("/mobile/**").permitAll()  // <-- allow mobile access without auth
//
//                ).permitAll()
//                        .requestMatchers("/admin/**", "/dashboard").hasRole("ADMIN")
//                        .requestMatchers("/mobile/**").authenticated() // ✅ protect mobile APIs
//                        .anyRequest().denyAll()
//                )
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
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //  Session-based
                );

        return http.build();
    }


//    // 🟢 Web Session-Based Login
//    @Bean
//    @Order(1)
//    public SecurityFilterChain webFilterChain(HttpSecurity http,
//                                              CustomLoginSuccessHandler customLoginSuccessHandler) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .userDetailsService(userDetailsService)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/signup", "/download", "/css/**", "/js/**").permitAll()
//                        .requestMatchers("/admin/**", "/dashboard").hasRole("ADMIN")
//                        .requestMatchers("/admin/medications/").hasRole("ADMIN")
//                        .anyRequest().denyAll()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .successHandler(customLoginSuccessHandler)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                );
//
//        return http.build();
//    }
//
//
//    @Bean
//    @Order(2)
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/mobile/secureData","/mobile/test-email","/mobile/is_verified","/mobile/verify","/mobile/login","/mobile/removeCarerFromClient","/api/medication/clients-with-medications/{carerId}","/api/medication/schedule/{clientId}", "api/medication/names/{clientId}","/mobile/**","/mobile/userCarer","mobile/check","api/clients","api/medications","api/medication/assign","/api/medications/import-openfda","/meds/**", "/login/**","/meds/details",
//                                "/api/auth/**","/mobile/user", "/signup", "/download", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//
//
////    // 🟢 Mobile JWT-Based Login
////    @Bean
////    @Order(2)
////    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers(
////                                "/mobile/check",
////                                "/mobile/user",
////                                "/mobile/userCarer",
////                                "/mobile/assignCarerToClient",
////                                "/mobile/removeCarerFromClient",
////                                "/api/**",
////                                "/meds/**",
////                                "/logs/**",
////                                "/login/**", "/signup", "/download", "/css/**", "/js/**"
////                        ).permitAll()
////                        .anyRequest().authenticated()
////                )
////
////                .sessionManagement(session -> session
////                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                )
////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}