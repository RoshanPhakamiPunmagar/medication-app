package com.example.meditime.security;

import com.example.meditime.repository.UserRepository;
import com.example.meditime.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
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
        // Skip JWT validation for any endpoint under /mobile
        if (path.startsWith("/mobile")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from Authorization header if it starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
        }
        // Validate token and set authentication if not already set in context
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user != null && jwtUtil.validateToken(jwt)) {
                // Create auth token with user's roles/authorities
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
