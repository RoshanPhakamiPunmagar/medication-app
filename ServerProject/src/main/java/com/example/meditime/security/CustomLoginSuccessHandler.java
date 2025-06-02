package com.example.meditime.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
/**
 * Handles successful user authentication by redirecting users
 * to different URLs based on their assigned roles.
 *
 * - Admin users are redirected to "/dashboard".
 * - Carer users are redirected to "/download".
 * - All other users are redirected to the login page with an unauthorized error.
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/dashboard");
        } else if (roles.contains("ROLE_CARER")) {
            response.sendRedirect("/download");
        } else {
            response.sendRedirect("/login?error=unauthorized");
        }
    }
}