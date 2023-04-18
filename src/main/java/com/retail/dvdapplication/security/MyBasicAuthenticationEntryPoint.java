package com.retail.dvdapplication.security;

import com.retail.dvdapplication.exception.MissingCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/*
* Custom Authentication Entry point used to handle security related exceptions
* Currently bugged: See issue #5 https://github.com/ttomtsis/DVDApplication/issues/5
*/
@Component
public class MyBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static Logger log = LoggerFactory.getLogger("Exception Handler");
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException{

        PrintWriter writer = response.getWriter();
        response.addHeader("WWW-Authenticate", "Basic realm= DVD-Store");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/plain");

        if ( authEx instanceof BadCredentialsException) {
            writer.println("HTTP Status 401 - " + " Provided credentials are invalid");
        }
        if ( authEx instanceof MissingCredentialsException ) {
            writer.println("HTTP Status 401 - " + " Missing required fields: " + authEx.getMessage());
        }
        if ( authEx instanceof InsufficientAuthenticationException ) {
            writer.println("HTTP Status 401 - " + " You need to login in order to access this resource");
        }



    }

}
