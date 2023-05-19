package com.retail.dvdapplication.configuration;

import com.retail.dvdapplication.domain.Employee;
import com.retail.dvdapplication.repository.EmployeeRepository;
import com.retail.dvdapplication.security.DatabaseAuthenticationManager;
import com.retail.dvdapplication.security.MyBasicAuthenticationEntryPoint;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/*
 * Security Configuration for the DVDApplication
 * Manages how authentication is handled.
 * Sets up and configures Basic Authentication for the app
 * also adds 2 pre-installed users, user t with password t
 * and user icsd15201 with password password132
 *
 * customAuthenticationEntryPoint is not used
 * despite being explicitly used in the exception translation filter
 * see issue #5: https://github.com/ttomtsis/DVDApplication/issues/5
*/
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // Custom Authentication entry point used to handle security related exceptions
    private final MyBasicAuthenticationEntryPoint customAuthenticationEntryPoint;

    // Will be used to add 2 pre-existing users in the create-users method
    private final EmployeeRepository repository;

    // Custom Authentication Manager, authenication logic implemented here
    private final DatabaseAuthenticationManager manager;

    SecurityConfiguration( DatabaseAuthenticationManager manager,
                           MyBasicAuthenticationEntryPoint customAuthenticationEntryPoint,
                           EmployeeRepository repository) {
        this.manager = manager;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.repository = repository;
    }

    // Creates the filter chain that will be used and configures which
    // endpoints will require authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                // login endpoint does not exist yet
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/server/health").permitAll()
                                .anyRequest().authenticated()
                )
                // csrf disabled and will not be implemented
                .csrf().disable();

        http.addFilter(new BasicAuthenticationFilter(manager));
        http.addFilter(new ExceptionTranslationFilter(customAuthenticationEntryPoint));

        return http.build();
    }

    // Creates 2 pre-existing users in the database, since user management is out of scope for this project
    @Bean
    @Transactional
    public boolean createUsers() {
        if ( repository.findByName("icsd15201") == null ) {
            repository.save(new Employee("icsd15201", "password"));

        }
        if ( repository.findByName("t") == null ) {
            repository.save(new Employee("t", "t"));
        }
        return true;
    }

}
