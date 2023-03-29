package com.retail.dvdapplication.security;

import com.retail.dvdapplication.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/* https://stackoverflow.com/questions/31826233/custom-authentication-manager-with-spring-security-and-java-configuration */

@Component
public class DatabaseAuthenticationManager implements AuthenticationManager {

    private UserRepository repository;
    private static final Logger log = LoggerFactory.getLogger("DATABASE AUTHENTICATION MANAGER");

    DatabaseAuthenticationManager (UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        log.info("New User Login Request");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info("Username: " + username + " Password: " + password);

        if (username.isEmpty()) {
            throw new InsufficientAuthenticationException("No username provided");
        }
        if (password.isEmpty()) {
            throw new InsufficientAuthenticationException("No password provided");
        }
        if (repository.findByNameAndPassword(username, password) != null) {
            log.info("User Found");
            Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }
        else {
            log.info("User not found");
            throw new BadCredentialsException("Provided credentials are invalid");
        }
    }


}
