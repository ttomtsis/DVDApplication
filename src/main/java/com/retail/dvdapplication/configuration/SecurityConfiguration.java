package com.retail.dvdapplication.configuration;

import com.retail.dvdapplication.domain.Employee;
import com.retail.dvdapplication.repository.EmployeeRepository;
import com.retail.dvdapplication.security.DatabaseAuthenticationManager;
import com.retail.dvdapplication.security.MyBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final MyBasicAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final EmployeeRepository repository;
    private final DatabaseAuthenticationManager manager;

    SecurityConfiguration( DatabaseAuthenticationManager manager,
                           MyBasicAuthenticationEntryPoint customAuthenticationEntryPoint,
                           EmployeeRepository repository) {
        this.manager = manager;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.repository = repository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf().disable();

        http.addFilter(new BasicAuthenticationFilter(manager));
        http.addFilter(new ExceptionTranslationFilter(customAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    public void createUsers() {
        if ( repository.findByName("icsd15201") == null ) {
            repository.save(new Employee("icsd15201", "password"));

        }
        if ( repository.findByName("t") == null ) {
            repository.save(new Employee("t", "t"));
        }
    }

}
