package com.VaadinTennisTournaments.application.data.entity.register;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class SecurityRegisterUser implements UserDetails {

    private RegisterUser registerUser;

    public SecurityRegisterUser(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }
    @Override
    public String getUsername() {
        return registerUser.getUsername();
    }
    @Override
    public String getPassword() {
        return registerUser.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(registerUser
                        .getRoles()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
