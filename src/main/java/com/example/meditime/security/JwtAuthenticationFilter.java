package com.example.meditime.security;


import com.example.meditime.model.User;
import com.example.meditime.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        String requestPath = request.getRequestURI();
        if (requestPath.startsWith("/mobile")) {

        if (path.startsWith("/mobile")) {
            filterChain.doFilter(request, response);
            return;
        }


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(jwt);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent() && jwtUtil.validateToken(jwt)) {
                User user = optionalUser.get();

                String role = jwtUtil.extractRole(jwt); // extract role from token
                List<GrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority(role)
                );

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                authorities
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}}
