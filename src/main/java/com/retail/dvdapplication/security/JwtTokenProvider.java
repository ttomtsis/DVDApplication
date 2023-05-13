package com.retail.dvdapplication.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class JwtTokenProvider {

// TODO: Add key pair used to sign the token
// TODO: Add token validation

    private boolean validateToken (JwtAuthenticationToken token) {
        return false;
    }

    private JwtAuthenticationToken generateToken(String username) throws JOSEException {

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

        // Create authorities
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // Return a custom token that contains the JWT
        return new JwtAuthenticationToken(jwtToken, authorities);
    }


}
