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
 * CustomLoginSuccessHandler handles logic after a successful authentication.
 * It redirects users to different pages depending on their roles.
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * This method is called when a user successfully logs in.
     * It checks the user's roles and redirects accordingly:
     * - Admin users are redirected to the dashboard.
     * - Carer users are redirected to the download page.
     * - Users without recognized roles are redirected back to login with an error.
     *
     * @param request        the HttpServletRequest
     * @param response       the HttpServletResponse
     * @param authentication the authenticated user's details
     * @throws IOException      if an input or output exception occurs
     * @throws ServletException if a servlet exception occurs
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Extract user roles from authentication object
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Redirect users based on their role
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/dashboard");  // Admin landing page
        } else if (roles.contains("ROLE_CARER")) {
            response.sendRedirect("/download");   // Carer landing page
        } else {
            // If user has no recognized role, redirect to login with error message
            response.sendRedirect("/login?error=unauthorized");
        }
    }
}
