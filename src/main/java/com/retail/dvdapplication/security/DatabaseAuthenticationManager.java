package com.retail.dvdapplication.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.retail.dvdapplication.exception.MissingCredentialsException;
import com.retail.dvdapplication.model.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/*
* Custom Authentication Manager, used to implement authentication logic
* The manager performs a query in the database to check if the credentials provided
* exist and thus the user is authenticated.
* Source :
* https://stackoverflow.com/questions/31826233/custom-authentication-manager-with-spring-security-and-java-configuration
*/
@Component
public class DatabaseAuthenticationManager implements AuthenticationManager {

    // Repository to perform db queries
    private EmployeeRepository repository;
    private static final Logger log = LoggerFactory.getLogger("DATABASE AUTHENTICATION MANAGER");

    DatabaseAuthenticationManager (EmployeeRepository repository) {
        this.repository = repository;
    }

    // Method used to implement custom authentication logic
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        log.info("New Employee Login Request");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info("Username: " + username + " Password: " + password);

        // Check if all credentials are provided, else throw error
        // See github issue #5 regarding security related exceptions
        // https://github.com/ttomtsis/DVDApplication/issues/5
        if (username.isEmpty()) {
            log.error("No username provided");
            throw new MissingCredentialsException("No username provided");
        }
        if (password.isEmpty()) {
            log.error("No password provided");
            throw new MissingCredentialsException("No password provided");
        }
        // If user's credentials exist in the database then
        // a new token is returned
        if (repository.findByNameAndPassword(username, password) != null) {
            log.info("Employee Found");
            Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

            // Generate a JWT
            Jwt jwtToken = null;
            try {
                jwtToken = generateJwt(username);
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }

            // Return a custom token that contains the JWT
            return new JwtAuthenticationToken(jwtToken, authorities);
        }
        // Else an exception is thrown
        else {
            log.warn("Employee not found");
            throw new BadCredentialsException("Provided credentials are invalid");
        }
    }

    private Jwt generateJwt(String username) throws JOSEException {

        long tenMinutesInMillis = 1000 * 60 * 10;
        Date expirationTime = new Date(new Date().getTime() + tenMinutesInMillis);

        // Create HMAC signer
        JWSSigner signer = new MACSigner("shared secret");

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                // TODO: Add user ID as subject
                .subject(username)
                .claim("name", username)
                .issueTime(new Date())
                .expirationTime(expirationTime)
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Sign the token, apply the HMAC protection
        signedJWT.sign(signer);

        // Serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        String jwtString = signedJWT.serialize();

        // Create a JWT Decoder
        JwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(new SecretKeySpec("shared secret".getBytes(), "HmacSHA256")).build();

        // Decode JWT string into Jwt object using NimbusJwtDecoder
        Jwt jwtToken = jwtDecoder.decode(jwtString);

        return jwtToken;
    }


}
