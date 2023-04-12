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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private MyBasicAuthenticationEntryPoint customAuthenticationEntryPoint;
    private EmployeeRepository repository;
    private DatabaseAuthenticationManager manager;

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
                                .requestMatchers("/**").permitAll()
                )
                .httpBasic(withDefaults())

                .csrf().disable();
/*              .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());*/

        BasicAuthenticationFilter base_auth_filter = new BasicAuthenticationFilter(manager);
        http.addFilter(base_auth_filter);

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
