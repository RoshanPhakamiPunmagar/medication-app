package com.example.meditime.security;

import com.example.meditime.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getUserId();
    }

    public String getName() {
        return user.getName();
    }

    public Long getRoleId() {
        return user.getRoleId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = switch (user.getRoleId().intValue()) {
            case 1 -> "ROLE_ADMIN";
            case 2 -> "ROLE_CARER";
            case 3 -> "ROLE_CLIENT";
            default -> "UNKNOWN";
        };
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
