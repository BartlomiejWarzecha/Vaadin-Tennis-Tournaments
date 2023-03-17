package com.VaadinTennisTournaments.application.security;

import com.VaadinTennisTournaments.application.data.entity.register.SecurityRegisterUser;
import com.VaadinTennisTournaments.application.data.repository.RegisterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    RegisterUserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(SecurityRegisterUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));


    }
}